package com.ndc.neostore.ui.feature.dashboard

sealed interface DashboardAction {
    data object OnLogout : DashboardAction
}