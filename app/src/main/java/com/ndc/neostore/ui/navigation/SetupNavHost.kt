package com.ndc.neostore.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ndc.neostore.ui.feature.auth.AuthEffect
import com.ndc.neostore.ui.feature.auth.AuthScreen
import com.ndc.neostore.ui.feature.auth.AuthViewModel

@Composable
fun SetupNavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        route = NavRoute.MainRoute.route,
        startDestination = NavRoute.AuthScreen.route,
    ) {
        composable(
            route = NavRoute.AuthScreen.route
        ) {
            val viewModel = hiltViewModel<AuthViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val effect by viewModel.onEffect.collectAsStateWithLifecycle(initialValue = AuthEffect.Empty)

            AuthScreen(
                navHostController = navHostController,
                state = state,
                effect = effect,
                action = viewModel::onAction,
            )
        }
    }
}