package com.ndc.neostore.ui.feature.dashboard

import androidx.lifecycle.viewModelScope
import com.ndc.neostore.base.BaseViewModel
import com.ndc.neostore.domain.GetCurrentUserUseCase
import com.ndc.neostore.domain.GetMarketProductUseCase
import com.ndc.neostore.domain.GetMyProductUseCase
import com.ndc.neostore.domain.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val getMyProductUseCase: GetMyProductUseCase,
    private val getMarketProductUseCase: GetMarketProductUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : BaseViewModel<DashboardState, DashboardAction, DashboardEffect>(DashboardState()) {

    init {
        getMyProduct()
        getMarketProduct()
        getCurrentUser()
    }

    override fun onAction(action: DashboardAction) {
        when (action) {
            DashboardAction.OnLogout -> logout()
            is DashboardAction.OnScreenChange -> updateState { copy(currentScreen = action.screen) }
            is DashboardAction.OnBottomSheetVisibilityChange -> updateState {
                copy(
                    homeBottomSheetType = action.type,
                    bottomSheetVisible = action.visible
                )
            }
        }
    }

    private fun getMyProduct() = viewModelScope.launch {
        getMyProductUseCase.invoke(
            onSuccess = {
                val updatedList = state.value.myProductList.toMutableList()
                updatedList.clear()
                updatedList.addAll(it)
                updateState { copy(myProductList = updatedList) }
            },
            onFailure = {
                onError(it)
            }
        )
    }

    private fun getMarketProduct() = viewModelScope.launch {
        getMarketProductUseCase.invoke(
            onSuccess = {
                val updatedList = state.value.marketProductDtoList.toMutableList()
                updatedList.clear()
                updatedList.addAll(it)
                updateState { copy(marketProductDtoList = updatedList) }
            },
            onFailure = {
                onError(it)
            }
        )
    }

    private fun getCurrentUser() = viewModelScope.launch {
        getCurrentUserUseCase.invoke(
            onSuccess = {
                updateState { copy(userDto = it) }
            },
            onFailure = {
                onError(it)
            }
        )
    }

    private fun logout() = viewModelScope.launch {
        logoutUseCase.invoke()
        sendEffect(DashboardEffect.OnLogout)
        delay(3000)
        sendEffect(DashboardEffect.Empty)
    }

    private fun onError(message: String) = viewModelScope.launch {
        sendEffect(DashboardEffect.OnError(message))
        delay(1000)
        sendEffect(DashboardEffect.Empty)
    }

}