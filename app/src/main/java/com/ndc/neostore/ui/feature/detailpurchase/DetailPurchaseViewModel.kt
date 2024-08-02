package com.ndc.neostore.ui.feature.detailpurchase

import androidx.lifecycle.viewModelScope
import com.ndc.neostore.base.BaseViewModel
import com.ndc.neostore.domain.CancelOrderUseCase
import com.ndc.neostore.domain.ConfirmOderUseCase
import com.ndc.neostore.domain.GetMyPurchaseOrderById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPurchaseViewModel @Inject constructor(
    private val cancelOrderUseCase: CancelOrderUseCase,
    private val confirmOrderUseCase: ConfirmOderUseCase,
    private val getMyPurchaseOrderById: GetMyPurchaseOrderById
) : BaseViewModel<DetailPurchaseState, DetailPurchaseAction, DetailPurchaseEffect>(
    DetailPurchaseState()
) {
    override fun onAction(action: DetailPurchaseAction) {
        when (action) {
            is DetailPurchaseAction.OnBottomSheetVisibilityChange -> updateState {
                copy(
                    bottomSheetVisible = action.visible
                )
            }

            DetailPurchaseAction.OnCancelOrder -> cancelOrder()
            DetailPurchaseAction.OnConfirmOrder -> confirmOrder()
            is DetailPurchaseAction.OnGetMyPurchaseOrderById -> getMyPurchaseById(action.orderId)
        }
    }

    private fun getMyPurchaseById(orderId: String) = viewModelScope.launch {
        updateState { copy(loadingState = true) }
        getMyPurchaseOrderById.invoke(
            orderId = orderId,
            onSuccess = {
                updateState {
                    copy(
                        loadingState = false,
                        mySalesOrderDto = it
                    )
                }
            },
            onFailure = {
                onShowToast(it)
            }
        )
    }

    private fun confirmOrder() = viewModelScope.launch {
        confirmOrderUseCase.invoke(
            orderId = state.value.mySalesOrderDto.orderId,
            onSuccess = {
                onShowToast("Berhasil mengubah status pesanan")
            },
            onFailure = {
                onShowToast(it)
            }
        )
    }

    private fun cancelOrder() = viewModelScope.launch {
        cancelOrderUseCase.invoke(
            orderId = state.value.mySalesOrderDto.orderId,
            onSuccess = {
                onShowToast("Berhasil membatalkan pesanan")
            },
            onFailure = {
                onShowToast(it)
            }
        )
    }

    private fun onShowToast(message: String) = viewModelScope.launch {
        updateState { copy(loadingState = false) }
        sendEffect(DetailPurchaseEffect.OnShowToast(message))
        delay(1000)
        sendEffect(DetailPurchaseEffect.Empty)
    }
}