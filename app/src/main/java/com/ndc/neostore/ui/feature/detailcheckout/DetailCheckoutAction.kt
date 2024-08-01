package com.ndc.neostore.ui.feature.detailcheckout

sealed interface DetailCheckoutAction {
    data class OnGetMarketProductById(
        val productId: String
    ) : DetailCheckoutAction
    data class OnAmountChange(
        val amount: Int
    ) : DetailCheckoutAction
    data object OnConfirmCheckout : DetailCheckoutAction
}