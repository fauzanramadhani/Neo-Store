package com.ndc.neostore.ui.navigation


const val keyA = "KEY_A"
const val keyB = "KEY_B"

sealed class NavRoute (val route: String) {
    data object MainRoute: NavRoute("MAIN_ROUTE")
    data object AuthScreen: NavRoute("AUTH_SCREEN")
    data object DashboardScreen: NavRoute("DASHBOARD_SCREEN")
    data object AddProductScreen: NavRoute("ADD_PRODUCT_SCREEN")
    data object DetailCheckOutScreen : NavRoute("DETAIL_CHECKOUT_SCREEN/{$keyA}") {
        fun navigateWithId(
            productId: String
        ) = "DETAIL_CHECKOUT_SCREEN/$productId"
    }
    data object TransactionScreen : NavRoute("TRANSACTION_SCREEN")
    data object DetailPurchaseScreen : NavRoute("DETAIL_PURCHASE_SCREEN/{$keyA}") {
        fun navigateWithId(
            orderId: String
        ) = "DETAIL_PURCHASE_SCREEN/$orderId"
    }
    data object DetailSalesScreen : NavRoute("DETAIL_SALES_SCREEN/{$keyA}") {
        fun navigateWithId(
            orderId: String
        ) = "DETAIL_SALES_SCREEN/$orderId"
    }
    data object EditProductScreen : NavRoute("EDIT_PRODUCT_SCREEN/{$keyA}") {
        fun navigateWithId(
            productId: String
        ) = "EDIT_PRODUCT_SCREEN/$productId"
    }
}