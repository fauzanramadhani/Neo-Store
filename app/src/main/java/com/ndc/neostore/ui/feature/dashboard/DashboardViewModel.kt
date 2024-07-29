package com.ndc.neostore.ui.feature.dashboard

import androidx.lifecycle.viewModelScope
import com.ndc.neostore.base.BaseViewModel
import com.ndc.neostore.domain.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel<DashboardState, DashboardAction, DashboardEffect>(DashboardState()) {
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

    private fun logout() = viewModelScope.launch {
        logoutUseCase.invoke()
        sendEffect(DashboardEffect.OnLogout)
        delay(3000)
        sendEffect(DashboardEffect.Empty)
    }

}