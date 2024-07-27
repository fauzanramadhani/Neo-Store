package com.ndc.neostore.ui.feature.auth

import android.content.Intent
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.ndc.neostore.base.BaseViewModel
import com.ndc.neostore.domain.HandleLoginWithGoogleUseCase
import com.ndc.neostore.domain.LoginBasicUseCase
import com.ndc.neostore.domain.RegisterUseCase
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
        }
    }

    private fun handleLoginWithGoogle(intent: Intent) = viewModelScope.launch {
        updateState { copy(loadingState = true) }
        try {
            handleLoginWithGoogleUseCase.invoke(intent).addOnSuccessListener {
                sendEffect(AuthEffect.OnSuccessAuth)
                updateState { copy(loadingState = false) }
            }.addOnFailureListener {
                sendEffect(AuthEffect.OnShowToast(it.message.toString()))
                updateState { copy(loadingState = false) }
            }
        } catch (e: Exception) {
            when (e.message.toString()) {
                "12501: " -> sendEffect(AuthEffect.OnShowToast("Operation Canceled by User"))
                else -> sendEffect(AuthEffect.OnShowToast(e.message.toString()))
            }
            updateState { copy(loadingState = false) }
        } finally {
            delay(3000)
            sendEffect(AuthEffect.Empty)
        }
    }

    private fun loginBasic() = viewModelScope.launch {
        updateState { copy(loadingState = true) }
        loginBasicUseCase.invoke(
            state.value.loginEmailValue,
            state.value.loginPasswordValue
        ).addOnSuccessListener { _ ->
            sendEffect(AuthEffect.OnSuccessAuth)
            updateState { copy(loadingState = false) }
        }.addOnFailureListener {
            sendEffect(AuthEffect.OnShowToast(it.message.toString()))
            updateState { copy(loadingState = false) }
        }
        delay(3000)
        sendEffect(AuthEffect.Empty)
    }

    private fun register() = viewModelScope.launch {
        updateState { copy(loadingState = true) }
        registerUseCase.invoke(
            state.value.registerEmailValue,
            state.value.registerPasswordValue
        ).addOnSuccessListener { _ ->
            updateState { copy(loadingState = false) }
            sendEffect(AuthEffect.OnSuccessAuth)
        }.addOnFailureListener {
            updateState { copy(loadingState = false) }
            sendEffect(AuthEffect.OnShowToast(it.message.toString()))
        }
        delay(3000)
        sendEffect(AuthEffect.Empty)
    }
}