package com.ndc.neostore.ui.feature.detailsales

sealed interface DetailSalesEffect {
    data object Empty : DetailSalesEffect
    data class OnShowToast (
        val message: String
    ) : DetailSalesEffect
}