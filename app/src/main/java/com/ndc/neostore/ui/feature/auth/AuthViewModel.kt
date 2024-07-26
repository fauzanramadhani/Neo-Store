package com.ndc.neostore.ui.feature.auth

import com.ndc.neostore.base.BaseViewModel
import com.ndc.neostore.data.source.local.sharedpref.SharedPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager
) : BaseViewModel<AuthState, AuthAction, AuthEffect>(AuthState()) {

    override fun onAction(action: AuthAction) {
        when (action) {
            is AuthAction.OnLoginEmailValueChange -> updateState { copy(loginEmailValue = action.value) }
            is AuthAction.OnLoginPasswordValueChange -> updateState { copy(loginPasswordValue = action.value) }
            is AuthAction.OnLoginPasswordVisibilityChange -> updateState { copy(loginPasswordVisible = action.visible) }
            is AuthAction.OnScreenChange -> updateState { copy(currentScreen = action.screen) }
            is AuthAction.OnLoginEmailStateChange -> updateState { copy(loginEmailState = action.state) }
            is AuthAction.OnLoginPasswordStateChange -> updateState { copy(loginPasswordState = action.state) }
            is AuthAction.OnRegisterEmailStateChange -> updateState { copy(registerEmailState = action.state) }
            is AuthAction.OnRegisterEmailValueChange -> updateState { copy(registerEmailValue = action.value) }
            is AuthAction.OnRegisterPasswordConfirmationStateChange -> updateState {
                copy(
                    registerPasswordConfirmationState = action.state
                )
            }

            is AuthAction.OnRegisterPasswordConfirmationValueChange -> updateState {
                copy(
                    registerPasswordConfirmationValue = action.value
                )
            }

            is AuthAction.OnRegisterPasswordConfirmationVisibilityChange -> updateState {
                copy(
                    registerPasswordConfirmationVisible = action.visible
                )
            }

            is AuthAction.OnRegisterPasswordStateChange -> updateState { copy(registerPasswordState = action.state) }
            is AuthAction.OnRegisterPasswordValueChange -> updateState { copy(registerPasswordValue = action.value) }
            is AuthAction.OnRegisterPasswordVisibilityChange -> updateState { copy(registerPasswordVisible = action.visible) }
        }
    }

}