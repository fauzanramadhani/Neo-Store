package com.ndc.neostore.domain

import com.ndc.neostore.data.repository.OrderRepository
import javax.inject.Inject

class CreateProductUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    operator fun invoke(
        sellerUid: String,
        productId: String,
        productName: String, // product name when making an order
        productPrice: Long, // product price when making an order
        orderAmount: Int,
        adminFee: Long,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) = orderRepository.createOrder(
        sellerUid,
        productId,
        productName,
        productPrice,
        orderAmount,
        adminFee,
        onSuccess,
        onFailure
    )
}