package com.ndc.neostore.data.source.network.firebase.dto

data class UserDto(
    val email: String = "",
    val name: String = "",
    val balance: Long = 0L,
    val profileUrl: String = "",
    val productId: List<String> = emptyList(),
    val orderId: List<String> = emptyList(),
    val uid: String = ""
)