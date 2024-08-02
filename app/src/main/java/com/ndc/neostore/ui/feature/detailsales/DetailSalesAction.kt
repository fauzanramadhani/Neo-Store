package com.ndc.neostore.ui.feature.detailsales

sealed interface DetailSalesAction {
    data class OnGetMySalesOrderById(
        val orderId: String
    ) : DetailSalesAction
    data object OnProcessOrder : DetailSalesAction
    data object OnCancelOrder : DetailSalesAction
    data class OnBottomSheetVisibilityChange(
        val visible: Boolean
    ) : DetailSalesAction
}