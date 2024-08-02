package com.ndc.neostore.ui.feature.detailpurchase


sealed interface DetailPurchaseAction {
    data class OnGetMyPurchaseOrderById(
        val orderId: String
    ) : DetailPurchaseAction

    data class OnBottomSheetVisibilityChange(
        val visible: Boolean
    ) : DetailPurchaseAction

    data object OnCancelOrder : DetailPurchaseAction
    data object OnConfirmOrder : DetailPurchaseAction
}