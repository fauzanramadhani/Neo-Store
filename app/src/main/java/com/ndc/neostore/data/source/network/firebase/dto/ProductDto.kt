package com.ndc.neostore.data.source.network.firebase.dto

data class ProductDto(
    val productId: String = "",
    val sellerUid: String = "",
    val productImageUrl: String = "",
    val productName: String = "",
    val productDescription: String = "",
    val productPrice: Long = 0,
    val productStock: Int = 0,
    val createdAt: Long = 0
)