package com.ndc.neostore.ui.feature.dashboard

sealed interface DashboardEffect {
    data object Empty: DashboardEffect
    data object OnLogout : DashboardEffect
    data class OnError(
        val message: String
    ): DashboardEffect
}