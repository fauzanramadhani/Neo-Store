package com.ndc.neostore.ui.feature.auth

import android.content.Intent
import com.ndc.neostore.ui.component.textfield.TextFieldState

sealed interface AuthAction {

    data class OnScreenChange(
        val screen: Int
    ) : AuthAction
    data object OnCheckPersonalization : AuthAction

    // Login Screen
    data class OnLoginEmailValueChange(
        val value: String
    ) : AuthAction
    data class OnLoginPasswordValueChange(
        val value: String
    ) : AuthAction
    data class OnLoginPasswordVisibilityChange(
        val visible: Boolean
    ) : AuthAction
    data class OnLoginEmailStateChange(
        val state: TextFieldState
    ) : AuthAction
    data class OnLoginPasswordStateChange(
        val state: TextFieldState
    ) : AuthAction
    data object OnLoginBasic : AuthAction
    data class OnHandleLoginWithGoogle(
        val intent: Intent
    ) : AuthAction


    // RegisterScreen
    data class OnRegisterEmailValueChange(
        val value: String
    ) : AuthAction
    data class OnRegisterPasswordValueChange(
        val value: String
    ) : AuthAction
    data class OnRegisterPasswordConfirmationValueChange(
        val value: String
    ) : AuthAction
    data class OnRegisterEmailStateChange(
        val state: TextFieldState
    ) : AuthAction
    data class OnRegisterPasswordStateChange(
        val state: TextFieldState
    ) : AuthAction
    data class OnRegisterPasswordConfirmationStateChange(
        val state: TextFieldState
    ) : AuthAction
    data class OnRegisterPasswordVisibilityChange(
        val visible: Boolean
    ) : AuthAction
    data class OnRegisterPasswordConfirmationVisibilityChange(
        val visible: Boolean
    ) : AuthAction
    data object OnRegister : AuthAction

    // Personalization Screen
    data class OnPersonalizationNameValueChange(
        val value: String
    ) : AuthAction
    data class OnPersonalizationNameStateChange(
        val state: TextFieldState
    ) : AuthAction
    data object OnPersonalizationSaved : AuthAction
    data class OnPersonalizationLogoutDialogVisibilityChange(
        val visible: Boolean
    ) : AuthAction
    data object OnPersonalizationLogout : AuthAction
}