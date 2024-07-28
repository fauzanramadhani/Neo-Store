package com.ndc.neostore.ui.feature.auth

import android.content.Intent
import com.ndc.neostore.ui.component.textfield.TextFieldState

data class AuthState(
    val currentScreen: Int = 0,
    val loadingState: Boolean = false,
    val gso: Intent? = null,
    // Login Screen
    val loginEmailValue: String = "",
    val loginEmailState: TextFieldState = TextFieldState.Empty,
    val loginPasswordValue: String = "",
    val loginPasswordState: TextFieldState = TextFieldState.Empty,
    val loginPasswordVisible: Boolean = false,
    val loginGoogleButtonEnabled: Boolean = true,
    // Register Screen
    val registerEmailValue: String = "",
    val registerEmailState: TextFieldState = TextFieldState.Empty,
    val registerPasswordValue: String = "",
    val registerPasswordState: TextFieldState = TextFieldState.Empty,
    val registerPasswordVisible: Boolean = false,
    val registerPasswordConfirmationValue: String = "",
    val registerPasswordConfirmationState: TextFieldState = TextFieldState.Empty,
    val registerPasswordConfirmationVisible: Boolean = false,
    // Personalization Screen
    val personalizationNameValue: String = "",
    val personalizationNameState: TextFieldState = TextFieldState.Empty,
    val personalizationLogoutDialogVisible: Boolean = false,
)
