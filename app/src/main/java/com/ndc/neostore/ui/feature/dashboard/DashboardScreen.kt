package com.ndc.neostore.ui.feature.dashboard

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.ndc.neostore.ui.component.button.PrimaryButton
import com.ndc.neostore.ui.navigation.NavRoute

@Composable
fun DashboardScreen(
    navHostController: NavHostController,
    state: DashboardState,
    effect: DashboardEffect,
    action: (DashboardAction) -> Unit,
) {
    val ctx = LocalContext.current

    BackHandler {
        (ctx as Activity).finish()
    }

    LaunchedEffect(effect) {
        when (effect) {
            DashboardEffect.Empty -> {}
            DashboardEffect.OnLogout -> navHostController.navigate(NavRoute.AuthScreen.route) {
                launchSingleTop = true
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        PrimaryButton(
            text = "Keluar"
        ) {
            action(DashboardAction.OnLogout)
        }
    }
}