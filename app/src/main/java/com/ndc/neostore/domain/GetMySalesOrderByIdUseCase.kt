package com.ndc.neostore.domain

import com.ndc.neostore.data.repository.OrderRepository
import com.ndc.neostore.data.source.network.firebase.dto.MySalesOrderDto
import javax.inject.Inject

class GetMySalesOrderByIdUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    operator fun invoke(
        orderId: String,
        onSuccess: (MySalesOrderDto) -> Unit,
        onFailure: (String) -> Unit
    ) = orderRepository.getMySalesOrderById(orderId, onSuccess, onFailure)
}