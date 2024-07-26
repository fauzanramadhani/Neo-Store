package com.ndc.neostore.ui.feature.auth

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.ndc.neostore.ui.feature.auth.screen.LoginScreen
import com.ndc.neostore.ui.feature.auth.screen.RegisterScreen

@Composable
fun AuthScreen(
    navHostController: NavHostController,
    state: AuthState,
    effect: AuthEffect,
    action: (AuthAction) -> Unit,
) {
    val typography = MaterialTheme.typography
    val color = MaterialTheme.colorScheme

    when (state.currentScreen) {
        0 -> LoginScreen(
            action = action,
            state = state,
            effect = effect
        )

        1 -> RegisterScreen(
            action = action,
            state = state,
            effect = effect
        )
    }
}