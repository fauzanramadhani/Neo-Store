package com.ndc.neostore.domain

import com.ndc.neostore.data.repository.ProductRepository
import com.ndc.neostore.data.source.network.firebase.dto.MarketProductDto
import javax.inject.Inject

class GetMarketProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(
        onSuccess: (List<MarketProductDto>) -> Unit,
        onFailure: (String) -> Unit
    ) = productRepository.getMarketProduct(onSuccess, onFailure)
}