package com.ndc.neostore.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ndc.neostore.ui.feature.addproduct.AddProductEffect
import com.ndc.neostore.ui.feature.addproduct.AddProductScreen
import com.ndc.neostore.ui.feature.addproduct.AddProductViewModel
import com.ndc.neostore.ui.feature.auth.AuthEffect
import com.ndc.neostore.ui.feature.auth.AuthScreen
import com.ndc.neostore.ui.feature.auth.AuthViewModel
import com.ndc.neostore.ui.feature.dashboard.DashboardEffect
import com.ndc.neostore.ui.feature.dashboard.DashboardScreen
import com.ndc.neostore.ui.feature.dashboard.DashboardViewModel
import com.ndc.neostore.ui.feature.detailcheckout.DetailCheckoutEffect
import com.ndc.neostore.ui.feature.detailcheckout.DetailCheckoutScreen
import com.ndc.neostore.ui.feature.detailcheckout.DetailCheckoutViewModel
import com.ndc.neostore.ui.feature.detailpurchase.DetailPurchaseEffect
import com.ndc.neostore.ui.feature.detailpurchase.DetailPurchaseScreen
import com.ndc.neostore.ui.feature.detailpurchase.DetailPurchaseViewModel
import com.ndc.neostore.ui.feature.detailsales.DetailSalesEffect
import com.ndc.neostore.ui.feature.detailsales.DetailSalesScreen
import com.ndc.neostore.ui.feature.detailsales.DetailSalesViewModel
import com.ndc.neostore.ui.feature.transaction.TransactionEffect
import com.ndc.neostore.ui.feature.transaction.TransactionScreen
import com.ndc.neostore.ui.feature.transaction.TransactionViewModel

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

        composable(
            route = NavRoute.DashboardScreen.route
        ) {
            val viewModel = hiltViewModel<DashboardViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val effect by viewModel.onEffect.collectAsStateWithLifecycle(initialValue = DashboardEffect.Empty)

            DashboardScreen(
                navHostController = navHostController,
                state = state,
                effect = effect,
                action = viewModel::onAction,
            )
        }

        composable(
            route = NavRoute.AddProductScreen.route
        ) {
            val viewModel = hiltViewModel<AddProductViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val effect by viewModel.onEffect.collectAsStateWithLifecycle(initialValue = AddProductEffect.Empty)

            AddProductScreen(
                navHostController = navHostController,
                state = state,
                effect = effect,
                action = viewModel::onAction,
            )
        }

        composable(
            route = NavRoute.DetailCheckOutScreen.route,
            arguments = listOf(
                navArgument(keyA) {
                    type = NavType.StringType
                }
            )
        ) {
            val viewModel = hiltViewModel<DetailCheckoutViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val effect by viewModel.onEffect.collectAsStateWithLifecycle(initialValue = DetailCheckoutEffect.Empty)
            val getProductId = it.arguments?.getString(keyA) ?: ""

            DetailCheckoutScreen(
                navHostController = navHostController,
                state = state,
                effect = effect,
                action = viewModel::onAction,
                productId = getProductId
            )
        }

        composable(
            route = NavRoute.TransactionScreen.route
        ) {
            val viewModel = hiltViewModel<TransactionViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val effect by viewModel.onEffect.collectAsStateWithLifecycle(initialValue = TransactionEffect.Empty)

            TransactionScreen(
                navHostController = navHostController,
                state = state,
                effect = effect,
                action = viewModel::onAction,
            )
        }

        composable(
            route = NavRoute.DetailPurchaseScreen.route,
            arguments = listOf(
                navArgument(keyA) {
                    type = NavType.StringType
                }
            )
        ) {
            val viewModel = hiltViewModel<DetailPurchaseViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val effect by viewModel.onEffect.collectAsStateWithLifecycle(initialValue = DetailPurchaseEffect.Empty)
            val getOrderId = it.arguments?.getString(keyA) ?: ""

            DetailPurchaseScreen(
                navHostController = navHostController,
                state = state,
                effect = effect,
                action = viewModel::onAction,
                orderId = getOrderId
            )
        }

        composable(
            route = NavRoute.DetailSalesScreen.route,
            arguments = listOf(
                navArgument(keyA) {
                    type = NavType.StringType
                }
            )
        ) {
            val viewModel = hiltViewModel<DetailSalesViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val effect by viewModel.onEffect.collectAsStateWithLifecycle(initialValue = DetailSalesEffect.Empty)
            val getOrderId = it.arguments?.getString(keyA) ?: ""

            DetailSalesScreen(
                navHostController = navHostController,
                state = state,
                effect = effect,
                action = viewModel::onAction,
                orderId = getOrderId
            )
        }
    }
}