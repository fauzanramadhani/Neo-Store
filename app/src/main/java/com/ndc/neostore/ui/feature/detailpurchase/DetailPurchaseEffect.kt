package com.ndc.neostore.ui.feature.detailpurchase

sealed interface DetailPurchaseEffect {
    data object Empty : DetailPurchaseEffect
    data class OnShowToast(
        val message: String
    ) : DetailPurchaseEffect
}