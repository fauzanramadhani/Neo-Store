package com.ndc.neostore.domain

import com.ndc.neostore.data.repository.ProductRepository
import com.ndc.neostore.data.source.network.firebase.dto.ProductDto
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(
        productId: String,
        onSuccess: (ProductDto) -> Unit,
        onFailure: (String) -> Unit
    ) = productRepository.getProductById(productId, onSuccess, onFailure)
}