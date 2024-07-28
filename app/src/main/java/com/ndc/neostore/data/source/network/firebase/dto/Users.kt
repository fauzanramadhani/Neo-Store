package com.ndc.neostore.data.source.network.firebase.dto

data class User(
    val email: String = "",
    val name: String = "",
    val orderId: List<String> = emptyList(),
    val uid: String = ""
)