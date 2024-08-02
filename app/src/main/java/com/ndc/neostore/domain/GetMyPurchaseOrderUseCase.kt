package com.ndc.neostore.domain

import com.ndc.neostore.data.repository.OrderRepository
import com.ndc.neostore.data.source.network.firebase.dto.MyPurchaseOrderDto
import javax.inject.Inject

class GetMyPurchaseOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    operator fun invoke(
        onSuccess: (List<MyPurchaseOrderDto>) -> Unit,
        onFailure: (String) -> Unit
    ) = orderRepository.getMyPurchaseOrder(onSuccess, onFailure)
}