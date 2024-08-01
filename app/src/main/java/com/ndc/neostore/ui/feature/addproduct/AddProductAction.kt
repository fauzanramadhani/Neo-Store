package com.ndc.neostore.ui.feature.addproduct

import android.net.Uri

sealed interface AddProductAction {
    data class OnImageProductChange(
        val imageUri: Uri
    ) : AddProductAction
    data class OnProductNameValueChange(
        val value: String
    ) : AddProductAction
    data class OnProductDescriptionValueChange(
        val value: String
    ) : AddProductAction
    data class OnProductPriceValueChange(
        val value: String
    ) : AddProductAction
    data class OnProductStockValueChange(
        val value: String
    ) : AddProductAction
    data object OnAddProduct : AddProductAction
}