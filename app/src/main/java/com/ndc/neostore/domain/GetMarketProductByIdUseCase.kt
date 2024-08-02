package com.ndc.neostore.domain

import com.ndc.neostore.data.repository.ProductRepository
import com.ndc.neostore.data.source.network.firebase.dto.MarketProductDto
import javax.inject.Inject

class GetMarketProductByIdUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(
        productId: String,
        onSuccess: (MarketProductDto) -> Unit,
        onFailure: (String) -> Unit
    ) = productRepository.getMarketProductById(productId, onSuccess, onFailure)
}