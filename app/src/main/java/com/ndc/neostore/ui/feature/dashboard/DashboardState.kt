package com.ndc.neostore.ui.feature.dashboard

data class DashboardState(
    val currentScreen: Int = 0,
    val bottomSheetVisible: Boolean = false,
    val homeBottomSheetType: HomeBottomSheetType = HomeBottomSheetType.NotReady
)
