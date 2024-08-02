package com.ndc.neostore.ui.feature.detailcheckout

import androidx.lifecycle.viewModelScope
import com.ndc.neostore.base.BaseViewModel
import com.ndc.neostore.domain.CreateProductUseCase
import com.ndc.neostore.domain.GetMarketProductByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailCheckoutViewModel @Inject constructor(
    private val getMarketProductByIdUseCase: GetMarketProductByIdUseCase,
    private val createProductUseCase: CreateProductUseCase
) : BaseViewModel<DetailCheckoutState, DetailCheckoutAction, DetailCheckoutEffect>(
    DetailCheckoutState()
) {
    override fun onAction(action: DetailCheckoutAction) {
        when (action) {
            is DetailCheckoutAction.OnGetMarketProductById -> getMarketProductById(action.productId)
            is DetailCheckoutAction.OnAmountChange -> {
                if (action.amount == state.value.marketProductDto.productStock) {
                    updateState { copy(buyAmountErrorState = true) }
                } else {
                    updateState { copy(buyAmountErrorState = false) }
                }
                updateState {
                    copy(buyAmount = action.amount)
                }
            }

            DetailCheckoutAction.OnConfirmCheckout -> createOrder()
        }
    }

    private fun getMarketProductById(productId: String) = viewModelScope.launch {
        getMarketProductByIdUseCase.invoke(
            productId = productId,
            onSuccess = {
                updateState { copy(marketProductDto = it) }
            },
            onFailure = {
                onError(it)
            }
        )
    }

    private fun createOrder() = viewModelScope.launch {
        val marketProductDto = state.value.marketProductDto

        updateState { copy(loadingState = true) }

        createProductUseCase.invoke(
            sellerUid = marketProductDto.sellerUid,
            productId = marketProductDto.productId,
            productImageUrl = marketProductDto.productImageUrl,
            productName = marketProductDto.productName,
            productPrice = marketProductDto.productPrice,
            orderAmount = state.value.buyAmount,
            adminFee = state.value.adminFee,
            onSuccess = {
                onSuccess()
                updateState { copy(loadingState = false) }
            },
            onFailure = {
                onError(it)
                updateState { copy(loadingState = false) }
            }
        )
    }

    private fun onSuccess() = viewModelScope.launch {
        sendEffect(DetailCheckoutEffect.OnSuccess)
        delay(1000)
        sendEffect(DetailCheckoutEffect.Empty)
    }

    private fun onError(message: String) = viewModelScope.launch {
        sendEffect(DetailCheckoutEffect.OnShowToast(message))
        delay(1000)
        sendEffect(DetailCheckoutEffect.Empty)
    }
}