package com.ndc.neostore.domain

import com.ndc.neostore.data.repository.OrderRepository
import com.ndc.neostore.data.source.network.firebase.dto.MyPurchaseOrderDto
import javax.inject.Inject

class GetMyPurchaseOrderById @Inject constructor(
    private val orderRepository: OrderRepository
) {
    operator fun invoke(
        orderId: String,
        onSuccess: (MyPurchaseOrderDto) -> Unit,
        onFailure: (String) -> Unit
    ) = orderRepository.getMyPurchaseOrderById(orderId, onSuccess, onFailure)
}