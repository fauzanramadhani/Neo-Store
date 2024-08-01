package com.ndc.neostore.data.repository

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import com.ndc.neostore.data.source.network.firebase.dto.OrderDto
import javax.inject.Inject

class OrderRepository @Inject constructor() {
    private val auth = Firebase.auth
    private val databaseRef = Firebase.database.reference

    fun createOrder(
        orderDto: OrderDto,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        databaseRef
            .child("orders")
            .child(orderDto.orderId)
            .setValue(orderDto)
            .addOnSuccessListener {
                val userOrderRef = databaseRef
                    .child("users")
                    .child("orderId")

                userOrderRef
                    .limitToLast(1)
                    .get()
                    .addOnSuccessListener { lastOrderIdSnapshot ->
                        val orderIdKey =
                            if (lastOrderIdSnapshot.exists())
                                lastOrderIdSnapshot
                                    .children
                                    .mapNotNull { it.key }
                                    .last()
                                    .toInt() + 1
                            else 0
                        userOrderRef
                            .child(orderIdKey.toString())
                            .setValue(orderDto.orderId)
                            .addOnSuccessListener {
                                onSuccess()
                            }
                            .addOnFailureListener {
                                onFailure(it.message.toString())
                            }
                    }.addOnFailureListener {
                        onFailure(it.message.toString())
                    }
            }
            .addOnFailureListener {
                onFailure(it.message.toString())
            }
    }
}