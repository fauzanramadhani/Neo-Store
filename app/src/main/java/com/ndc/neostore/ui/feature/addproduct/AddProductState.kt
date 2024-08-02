package com.ndc.neostore.ui.feature.addproduct

import android.net.Uri

data class AddProductState(
    val productImageUri: Uri? = null,
    val productNameValue: String = "",
    val productDescriptionValue: String = "",
    val productPriceValue: String = "",
    val productStockValue: String = "",
    val loadingState: Boolean = false,
)
