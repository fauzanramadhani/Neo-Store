package com.ndc.neostore.ui.feature.editproduct

import com.ndc.neostore.data.source.network.firebase.dto.ProductDto

data class EditProductState(
    val loadingState: Boolean = false,
    val productDto: ProductDto = ProductDto(),
    val productImage: Any = "",
    val productNameValue: String = "",
    val productDescriptionValue: String = "",
    val productPriceValue: String = "",
    val productStockValue: String = "",
) {
    fun isSame(): Boolean = with(this) {
        productNameValue == productDto.productName &&
                productDescriptionValue == productDto.productDescription &&
                productPriceValue == productDto.productPrice.toString() &&
                productStockValue == productDto.productStock.toString() &&
                productImage == productDto.productImageUrl
    }
}
