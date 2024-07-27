package com.ndc.neostore.ui.feature.auth

sealed interface AuthEffect {
    data object Empty: AuthEffect
    data class OnShowToast(
        val message: String
    ) : AuthEffect
    data object OnSuccessAuth : AuthEffect
}