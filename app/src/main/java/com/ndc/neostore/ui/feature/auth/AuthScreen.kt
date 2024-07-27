package com.ndc.neostore.ui.feature.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.ndc.neostore.ui.component.dialog.DialogLoading
import com.ndc.neostore.ui.feature.auth.screen.LoginScreen
import com.ndc.neostore.ui.feature.auth.screen.RegisterScreen
import com.ndc.neostore.ui.navigation.NavRoute
import com.ndc.neostore.utils.Toast

@Composable
fun AuthScreen(
    navHostController: NavHostController,
    state: AuthState,
    effect: AuthEffect,
    action: (AuthAction) -> Unit,
) {
    val ctx = LocalContext.current

    fun navigateToDashboard() = navHostController.navigate(NavRoute.DashboardScreen.route) {
        launchSingleTop = true
    }

    if (Firebase.auth.currentUser?.uid != null) {
        navigateToDashboard()
    } else {
        LaunchedEffect(effect) {
            when (effect) {
                AuthEffect.Empty -> {}
                is AuthEffect.OnShowToast -> Toast(ctx, effect.message).short()
                AuthEffect.OnSuccessAuth -> navigateToDashboard()
            }
        }

        DialogLoading(visible = state.loadingState)

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
}