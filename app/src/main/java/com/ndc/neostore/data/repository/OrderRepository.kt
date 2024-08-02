package com.ndc.neostore.data.repository

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.ndc.neostore.data.source.network.firebase.dto.MyPurchaseOrderDto
import com.ndc.neostore.data.source.network.firebase.dto.MySalesOrderDto
import com.ndc.neostore.data.source.network.firebase.dto.OrderDto
import com.ndc.neostore.data.source.network.firebase.dto.OrderStatus
import com.ndc.neostore.data.source.network.firebase.dto.UserDto
import com.ndc.neostore.utils.generateRandomId
import javax.inject.Inject

class OrderRepository @Inject constructor() {
    private val auth = Firebase.auth
    private val databaseRef = Firebase.database.reference

    fun createOrder(
        sellerUid: String,
        productId: String,
        productImageUrl: String,  // product image when making an order
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
            productImageUrl = productImageUrl,
            productPrice = productPrice,
            orderAmount = orderAmount,
            adminFee = adminFee,
            orderStatus = OrderStatus.Dibayar.name,
            createdAt = System.currentTimeMillis()
        )

        databaseRef
            .child("product")
            .child(productId)
            .child("productStock")
            .get()
            .addOnSuccessListener { productStockSnapshot ->
                val productStock = productStockSnapshot.getValue(Int::class.java) ?: 1
                databaseRef
                    .child("users")
                    .child(buyerUid)
                    .child("balance")
                    .get()
                    .addOnSuccessListener { userBalanceSnapshot ->
                        val userBalance = userBalanceSnapshot.getValue(Long::class.java)
                        val updates = mutableMapOf<String, Any>()
                        val total = (productPrice * orderAmount) + adminFee

                        if (userBalance == null || userBalance < total) {
                            onFailure("Saldo Neo Pay anda tidak cukup, silahkan lakukan topup")
                        } else {
                            updates["users/$sellerUid/orders/$orderUid"] = orderUid
                            updates["users/$buyerUid/orders/$orderUid"] = orderUid
                            updates["product/$productId/productStock"] = productStock - orderAmount
                            updates["orders/$orderUid"] = orderDto
                            updates["users/$buyerUid/balance"] = userBalance - total

                            databaseRef.updateChildren(updates).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    onSuccess()
                                } else {
                                    onFailure(task.exception?.message.toString())
                                }
                            }
                        }

                    }.addOnFailureListener {
                        onFailure(it.message.toString())
                    }
            }.addOnFailureListener {
                onFailure(it.message.toString())
            }
    }

    fun getMyPurchaseOrder(
        onSuccess: (List<MyPurchaseOrderDto>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val currentUid = auth.currentUser?.uid ?: ""
        databaseRef
            .child("orders")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.childrenCount == 0L) {
                        onSuccess(emptyList())
                    }
                    val orderDtoList = mutableListOf<OrderDto>()
                    val myPurchaseOrderDtoList = mutableListOf<MyPurchaseOrderDto>()
                    snapshot.children.forEach { orderSnapshot ->
                        val orderDto = orderSnapshot.getValue(OrderDto::class.java) ?: OrderDto()
                        orderDtoList.add(orderDto)
                        if (orderDto.buyerId == currentUid) {
                            databaseRef
                                .child("users")
                                .child(orderDto.sellerId)
                                .get()
                                .addOnSuccessListener { userSnapshot ->
                                    val userDto =
                                        userSnapshot.getValue(UserDto::class.java) ?: UserDto()
                                    val myPurchaseOrderDto = MyPurchaseOrderDto(
                                        orderId = orderDto.orderId,
                                        sellerId = orderDto.sellerId,
                                        sellerName = userDto.name,
                                        sellerProfileUrl = userDto.profileUrl,
                                        productId = orderDto.productId,
                                        productImageUrl = orderDto.productImageUrl,
                                        productName = orderDto.productName,
                                        productPrice = orderDto.productPrice,
                                        orderAmount = orderDto.orderAmount,
                                        adminFee = orderDto.adminFee,
                                        orderStatus = orderDto.orderStatus,
                                        createdAt = orderDto.createdAt
                                    )
                                    myPurchaseOrderDtoList.add(myPurchaseOrderDto)
                                    if (orderDtoList.size.toLong() == snapshot.childrenCount)
                                        onSuccess(myPurchaseOrderDtoList.sortedByDescending { it.createdAt })
                                }.addOnFailureListener {
                                    onFailure(it.message.toString())
                                }
                        } else {
                            if (orderDtoList.size.toLong() == snapshot.childrenCount)
                                onSuccess(myPurchaseOrderDtoList.sortedByDescending { it.createdAt })
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    onFailure(error.message)
                }
            })
    }

    fun getMySalesOrder(
        onSuccess: (List<MySalesOrderDto>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val currentUid = auth.currentUser?.uid ?: ""
        databaseRef
            .child("orders")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.childrenCount == 0L) {
                        onSuccess(emptyList())
                    }
                    val orderDtoList = mutableListOf<OrderDto>()
                    val mySalesOrderList = mutableListOf<MySalesOrderDto>()
                    snapshot.children.forEach { orderSnapshot ->
                        val orderDto = orderSnapshot.getValue(OrderDto::class.java) ?: OrderDto()
                        orderDtoList.add(orderDto)
                        if (orderDto.sellerId == currentUid) {
                            databaseRef
                                .child("users")
                                .child(orderDto.buyerId)
                                .get()
                                .addOnSuccessListener { userSnapshot ->
                                    val userDto =
                                        userSnapshot.getValue(UserDto::class.java) ?: UserDto()
                                    val mySalesOrderDto = MySalesOrderDto(
                                        orderId = orderDto.orderId,
                                        buyerId = orderDto.buyerId,
                                        buyerName = userDto.name,
                                        buyerProfileUrl = userDto.profileUrl,
                                        productId = orderDto.productId,
                                        productImageUrl = orderDto.productImageUrl,
                                        productName = orderDto.productName,
                                        productPrice = orderDto.productPrice,
                                        orderAmount = orderDto.orderAmount,
                                        adminFee = orderDto.adminFee,
                                        orderStatus = orderDto.orderStatus,
                                        createdAt = orderDto.createdAt
                                    )
                                    mySalesOrderList.add(mySalesOrderDto)
                                    if (orderDtoList.size.toLong() == snapshot.childrenCount) {
                                        onSuccess(mySalesOrderList)
                                    }
                                }.addOnFailureListener {
                                    onFailure(it.message.toString())
                                }
                        } else {
                            if (orderDtoList.size.toLong() == snapshot.childrenCount) {
                                onSuccess(mySalesOrderList)
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    onFailure(error.message)
                }
            })
    }
}