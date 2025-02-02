package com.ndc.neostore.data.source.network.firebase.dto

data class OrderDto(
    val orderId: String = "",
    val sellerId: String = "",
    val buyerId: String = "",
    val productId: String = "",
    val productImageUrl: String = "", // product image when making an order
    val productName: String = "", // product name when making an order
    val productPrice: Long = 0, // product price when making an order
    val orderAmount: Int = 0,
    val adminFee: Long = 0,
    val orderStatus: String = "",
    val createdAt: Long = 0L
)

enum class OrderStatus {
    Dibayar,
    Diproses,
    Selesai,
    Dibatalkan
}