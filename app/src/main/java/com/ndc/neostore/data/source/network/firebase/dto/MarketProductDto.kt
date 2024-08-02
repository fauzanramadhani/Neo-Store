package com.ndc.neostore.data.source.network.firebase.dto

data class MarketProductDto(
    val productId: String = "",
    val sellerUid: String = "",
    val sellerName: String = "",
    val sellerProfileUrl: String = "",
    val productImageUrl: String = "",
    val productName: String = "",
    val productDescription: String = "",
    val productPrice: Long = 0,
    val productStock: Int = 0,
    val createdAt: Long = 0
)
