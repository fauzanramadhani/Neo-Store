package com.ndc.neostore.domain

import com.ndc.neostore.data.repository.OrderRepository
import javax.inject.Inject

class ProcessOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    operator fun invoke(
        orderId: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) = orderRepository.processOrder(orderId, onSuccess, onFailure)
}