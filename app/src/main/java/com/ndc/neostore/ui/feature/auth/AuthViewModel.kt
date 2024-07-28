package com.ndc.neostore.ui.feature.auth

import android.content.Intent
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.ndc.neostore.base.BaseViewModel
import com.ndc.neostore.domain.CheckPersonalizationUseCase
import com.ndc.neostore.domain.HandleLoginWithGoogleUseCase
import com.ndc.neostore.domain.LoginBasicUseCase
import com.ndc.neostore.domain.LogoutUseCase
import com.ndc.neostore.domain.RegisterUseCase
import com.ndc.neostore.domain.SetUserNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val handleLoginWithGoogleUseCase: HandleLoginWithGoogleUseCase,
    private val loginBasicUseCase: LoginBasicUseCase,
    private val registerUseCase: RegisterUseCase,
    private val googleSignInClient: GoogleSignInClient,
    private val checkPersonalizationUseCase: CheckPersonalizationUseCase,
    private val setUserNameUseCase: SetUserNameUseCase,
    private val logoutUseCase: LogoutUseCase,
) : BaseViewModel<AuthState, AuthAction, AuthEffect>(AuthState()) {

    init {
        updateState {
            copy(
                gso = googleSignInClient.signInIntent
            )
        }
    }

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
            is AuthAction.OnRegisterPasswordVisibilityChange -> updateState {
                copy(
                    registerPasswordVisible = action.visible
                )
            }

            is AuthAction.OnHandleLoginWithGoogle -> handleLoginWithGoogle(action.intent)
            AuthAction.OnLoginBasic -> loginBasic()
            AuthAction.OnRegister -> register()
            is AuthAction.OnPersonalizationNameStateChange -> updateState {
                copy(
                    personalizationNameState = action.state
                )
            }

            is AuthAction.OnPersonalizationNameValueChange -> updateState {
                copy(
                    personalizationNameValue = action.value
                )
            }

            AuthAction.OnCheckPersonalization -> {
                checkPersonalization()
            }

            AuthAction.OnPersonalizationSaved -> setUserName()
            is AuthAction.OnPersonalizationLogoutDialogVisibilityChange -> updateState {
                copy(
                    personalizationLogoutDialogVisible = action.visible
                )
            }

            AuthAction.OnPersonalizationLogout -> logout()
        }
    }

    private fun logout() = viewModelScope.launch {
        logoutUseCase.invoke()
        updateState {
            copy(
                currentScreen = 0,
                personalizationLogoutDialogVisible = false
            )
        }
    }

    private fun setUserName() = viewModelScope.launch {
        updateState { copy(loadingState = true) }
        setUserNameUseCase.invoke(
            name = state.value.personalizationNameValue,
            onSuccess = {
                onSuccessAuth()
            },
            onFailure = {
                onShowToast(it)
            }
        )
    }

    private fun checkPersonalization() = viewModelScope.launch {
        checkPersonalizationUseCase.invoke(
            onSuccess = {
                if (it) onSuccessAuth()
                else updateState { copy(currentScreen = 2) }
                updateState { copy(loadingState = false) }
            },
            onFailure = {
                onShowToast(it)
            }
        )
    }

    private fun handleLoginWithGoogle(intent: Intent) = viewModelScope.launch {
        updateState { copy(loadingState = true) }
        try {
            handleLoginWithGoogleUseCase.invoke(intent).addOnSuccessListener {
                checkPersonalization()
            }.addOnFailureListener {
                onShowToast(it.message.toString())
                updateState { copy(loadingState = false) }
            }
        } catch (e: Exception) {
            when (e.message.toString()) {
                "12501: " -> sendEffect(AuthEffect.OnShowToast("Operation Canceled by User"))
                else -> sendEffect(AuthEffect.OnShowToast(e.message.toString()))
            }
            updateState { copy(loadingState = false) }
        }
    }

    private fun loginBasic() = viewModelScope.launch {
        updateState { copy(loadingState = true) }
        loginBasicUseCase.invoke(
            state.value.loginEmailValue,
            state.value.loginPasswordValue
        ).addOnSuccessListener { _ ->
            checkPersonalization()
        }.addOnFailureListener {
            onShowToast(it.message.toString())
            updateState { copy(loadingState = false) }
        }
    }

    private fun register() = viewModelScope.launch {
        updateState { copy(loadingState = true) }
        registerUseCase.invoke(
            state.value.registerEmailValue,
            state.value.registerPasswordValue
        ).addOnSuccessListener { _ ->
            updateState {
                copy(
                    loadingState = false,
                    currentScreen = 2
                )
            }

        }.addOnFailureListener {
            updateState { copy(loadingState = false) }
            onShowToast(it.message.toString())
        }

    }

    private fun onSuccessAuth() = viewModelScope.launch {
        sendEffect(AuthEffect.OnSuccessAuth)
        delay(3000)
        sendEffect(AuthEffect.Empty)
    }

    private fun onShowToast(message: String) = viewModelScope.launch {
        sendEffect(AuthEffect.OnShowToast(message))
        delay(3000)
        sendEffect(AuthEffect.Empty)
    }
}