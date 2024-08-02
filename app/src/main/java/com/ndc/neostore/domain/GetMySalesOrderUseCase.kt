package com.ndc.neostore.domain

import com.ndc.neostore.data.repository.OrderRepository
import com.ndc.neostore.data.source.network.firebase.dto.MySalesOrderDto
import com.ndc.neostore.data.source.network.firebase.dto.OrderDto
import javax.inject.Inject

class GetMySalesOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    operator fun invoke(
        onSuccess: (List<MySalesOrderDto>) -> Unit,
        onFailure: (String) -> Unit
    ) = orderRepository.getMySalesOrder(onSuccess, onFailure)
}