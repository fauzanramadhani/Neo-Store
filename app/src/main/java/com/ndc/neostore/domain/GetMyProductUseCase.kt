package com.ndc.neostore.domain

import com.ndc.neostore.data.repository.ProductRepository
import com.ndc.neostore.data.source.network.firebase.dto.ProductDto
import javax.inject.Inject

class GetMyProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(
        onSuccess: (List<ProductDto>) -> Unit,
        onFailure: (String) -> Unit
    ) = productRepository.getMyProduct(onSuccess, onFailure)
}