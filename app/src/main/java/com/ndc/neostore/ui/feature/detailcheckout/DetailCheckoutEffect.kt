package com.ndc.neostore.ui.feature.detailcheckout

sealed interface DetailCheckoutEffect {
    data class OnShowToast(
        val message: String
    ) : DetailCheckoutEffect
    data object Empty: DetailCheckoutEffect
}