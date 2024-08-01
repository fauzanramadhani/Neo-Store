package com.ndc.neostore.data.repository

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import com.ndc.neostore.data.source.network.firebase.dto.OrderDto
import com.ndc.neostore.utils.generateRandomId
import javax.inject.Inject

class OrderRepository @Inject constructor() {
    private val auth = Firebase.auth
    private val databaseRef = Firebase.database.reference

    fun createOrder(
        sellerUid: String,
        productId: String,
        productName: String, // product name when making an order
        productPrice: Long, // product price when making an order
        orderAmount: Int,
        adminFee: Long,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val buyerUid = auth.currentUser?.uid ?: ""
        val orderUid = generateRandomId("ORDER")
        val orderDto = OrderDto(
            orderId = orderUid,
            sellerId = sellerUid,
            buyerId = buyerUid,
            productId = productId,
            productName = productName,
            productPrice = productPrice,
            orderAmount = orderAmount,
            adminFee = adminFee
        )

        databaseRef
            .child("product")
            .child(productId)
            .child("productStock")
            .get()
            .addOnSuccessListener { productStockSnapshot ->
                val productStock = productStockSnapshot.getValue(Int::class.java)
                val updates = mutableMapOf<String, Any>()

                updates["users/$sellerUid/orders/$orderUid"] = orderUid
                updates["users/$buyerUid/orders/$orderUid"] = orderUid
                productStock?.let {
                    updates["product/$productId/productStock"] = it - 1
                }
                updates["orders/$orderUid"] = orderDto

                databaseRef.updateChildren(updates).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onSuccess()
                    } else {
                        onFailure(task.exception?.message.toString())
                    }
                }
            }.addOnFailureListener {
                onFailure(it.message.toString())
            }
    }
}