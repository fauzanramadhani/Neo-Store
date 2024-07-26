package com.ndc.neostore.ui.feature.auth

sealed interface AuthEffect {
    data object Empty: AuthEffect
}