package com.ndc.neostore.ui.feature.detailsales

import androidx.lifecycle.viewModelScope
import com.ndc.neostore.base.BaseViewModel
import com.ndc.neostore.domain.CancelOrderUseCase
import com.ndc.neostore.domain.GetMySalesOrderByIdUseCase
import com.ndc.neostore.domain.ProcessOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailSalesViewModel @Inject constructor(
    private val getMySalesOrderByIdUseCase: GetMySalesOrderByIdUseCase,
    private val processOrderUseCase: ProcessOrderUseCase,
    private val cancelOrderUseCase: CancelOrderUseCase
) : BaseViewModel<DetailSalesState, DetailSalesAction, DetailSalesEffect>(
    DetailSalesState()
) {

    override fun onAction(action: DetailSalesAction) {
        when (action) {
            DetailSalesAction.OnCancelOrder -> cancelOrder()
            DetailSalesAction.OnProcessOrder -> processOrder()
            is DetailSalesAction.OnBottomSheetVisibilityChange -> updateState {
                copy(
                    bottomSheetVisible = action.visible
                )
            }

            is DetailSalesAction.OnGetMySalesOrderById -> getMySalesOrderById(action.orderId)
        }
    }

    private fun getMySalesOrderById(id: String) = viewModelScope.launch {
        updateState { copy(loadingState = true) }
        getMySalesOrderByIdUseCase.invoke(
            orderId = id,
            onSuccess = {
                updateState {
                    copy(
                        mySalesOrderDto = it,
                        loadingState = false
                    )
                }
            },
            onFailure = {
                onShowToast(it)
            }
        )
    }

    private fun processOrder() = viewModelScope.launch {
        processOrderUseCase.invoke(
            orderId = state.value.mySalesOrderDto.orderId,
            onSuccess = {
                onShowToast("Berhasil mengubah status")
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
        sendEffect(DetailSalesEffect.OnShowToast(message))
        delay(1000)
        sendEffect(DetailSalesEffect.Empty)
    }
}