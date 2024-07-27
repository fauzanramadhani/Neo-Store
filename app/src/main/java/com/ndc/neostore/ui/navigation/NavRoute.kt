package com.ndc.neostore.ui.navigation

sealed class NavRoute (val route: String) {
    data object MainRoute: NavRoute("MAIN_ROUTE")
    data object AuthScreen: NavRoute("AUTH_SCREEN")
    data object DashboardScreen: NavRoute("DASHBOARD_SCREEN")
}