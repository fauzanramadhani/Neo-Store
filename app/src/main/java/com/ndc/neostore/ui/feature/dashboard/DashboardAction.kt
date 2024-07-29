package com.ndc.neostore.ui.feature.dashboard

sealed interface DashboardAction {
    data object OnLogout : DashboardAction
    data class OnScreenChange(
        val screen: Int
    ) : DashboardAction
    data class OnBottomSheetVisibilityChange(
        val visible: Boolean,
        val type: HomeBottomSheetType
    ) : DashboardAction
}