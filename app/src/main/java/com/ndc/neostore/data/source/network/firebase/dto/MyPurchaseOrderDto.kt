package com.ndc.neostore.data.source.network.firebase.dto

data class MyPurchaseOrderDto(
    val orderId: String = "",
    val sellerId: String = "",
    val sellerName: String = "",
    val sellerProfileUrl: String = "",
    val productId: String = "",
    val productName: String = "",
    val productImageUrl: String = "",
    val productPrice: Long = 0,
    val orderAmount: Int = 0,
    val adminFee: Long = 0,
    val orderStatus: String = "",
    val createdAt: Long = 0L
)
