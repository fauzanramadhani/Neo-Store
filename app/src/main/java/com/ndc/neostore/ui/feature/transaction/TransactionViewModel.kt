package com.ndc.neostore.ui.feature.transaction

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ndc.neostore.base.BaseViewModel
import com.ndc.neostore.domain.GetMyPurchaseOrderUseCase
import com.ndc.neostore.domain.GetMySalesOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val getMyPurchaseOrderUseCase: GetMyPurchaseOrderUseCase,
    private val getMySalesOrderUseCase: GetMySalesOrderUseCase
): BaseViewModel<TransactionState, TransactionAction, TransactionEffect>(
    TransactionState()
) {
    init {
        getMyPurchaseOrderList()
        getMySalesOrderList()
    }

    override fun onAction(action: TransactionAction) {

    }

    private fun getMyPurchaseOrderList() = viewModelScope.launch {
        updateState { copy(loadingState = true) }
        getMyPurchaseOrderUseCase.invoke(
            onSuccess = {
                updateState { copy(myPurchaseOrderList = it) }
                updateState { copy(loadingState = false) }
            },
            onFailure = {
                onError(it)
            }
        )
    }

    private fun getMySalesOrderList() = viewModelScope.launch {
        updateState { copy(loadingState = true) }
        getMySalesOrderUseCase.invoke(
            onSuccess = {
                updateState { copy(mySalesOrderList = it) }
                updateState { copy(loadingState = false) }
            },
            onFailure = {
                onError(it)
            }
        )
    }

    private fun onError(message: String) = viewModelScope.launch {
        updateState { copy(loadingState = false) }
        sendEffect(TransactionEffect.OnShowToast(message))
        delay(1000)
        sendEffect(TransactionEffect.Empty)
    }
}