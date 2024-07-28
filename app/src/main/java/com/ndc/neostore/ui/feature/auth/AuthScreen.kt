package com.ndc.neostore.ui.feature.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.ndc.neostore.ui.component.dialog.DialogConfirmation
import com.ndc.neostore.ui.component.dialog.DialogLoading
import com.ndc.neostore.ui.feature.auth.screen.LoginScreen
import com.ndc.neostore.ui.feature.auth.screen.PersonalizationScreen
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

    LaunchedEffect(effect) {
        when (effect) {
            AuthEffect.Empty -> {}
            is AuthEffect.OnShowToast -> Toast(ctx, effect.message).short()
            AuthEffect.OnSuccessAuth -> navigateToDashboard()
        }
    }

    DialogLoading(visible = state.loadingState)

    if (Firebase.auth.currentUser?.uid != null) {
        action(AuthAction.OnCheckPersonalization)
        when (state.currentScreen) {
            2 -> PersonalizationScreen(
                action = action,
                state = state,
            )
        }
    } else {

        when (state.currentScreen) {
            0 -> LoginScreen(
                action = action,
                state = state,
            )

            1 -> RegisterScreen(
                action = action,
                state = state,
            )
        }
    }
}