package com.ndc.neostore.data.source.network.firebase.dto

data class MySalesOrderDto(
    val orderId: String = "",
    val buyerId: String = "",
    val buyerName: String = "",
    val buyerProfileUrl: String = "",
    val productId: String = "",
    val productImageUrl: String = "",
    val productName: String = "",
    val productPrice: Long = 0,
    val orderAmount: Int = 0,
    val adminFee: Long = 0,
    val orderStatus: String = "",
    val createdAt: Long = 0L
)
