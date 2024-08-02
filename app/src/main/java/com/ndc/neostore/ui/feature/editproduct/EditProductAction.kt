package com.ndc.neostore.ui.feature.editproduct

import android.net.Uri

sealed interface EditProductAction {
    data class OnGetProduct(
        val productId: String
    ) : EditProductAction

    data class OnImageProductChange(
        val imageUri: Uri
    ) : EditProductAction

    data class OnProductNameValueChange(
        val value: String
    ) : EditProductAction

    data class OnProductDescriptionValueChange(
        val value: String
    ) : EditProductAction

    data class OnProductPriceValueChange(
        val value: String
    ) : EditProductAction

    data class OnProductStockValueChange(
        val value: String
    ) : EditProductAction

    data object OnEditProduct : EditProductAction
}