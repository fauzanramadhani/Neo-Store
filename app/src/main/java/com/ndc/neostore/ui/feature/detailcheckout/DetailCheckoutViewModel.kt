package com.ndc.neostore.ui.feature.detailcheckout

import androidx.lifecycle.viewModelScope
import com.ndc.neostore.base.BaseViewModel
import com.ndc.neostore.domain.GetMarketProductByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailCheckoutViewModel @Inject constructor(
    private val getMarketProductByIdUseCase: GetMarketProductByIdUseCase
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

    private fun onError(message: String) = viewModelScope.launch {
        sendEffect(DetailCheckoutEffect.OnShowToast(message))
        delay(1000)
        sendEffect(DetailCheckoutEffect.Empty)
    }

    private fun createOrder() = viewModelScope.launch {

    }
}