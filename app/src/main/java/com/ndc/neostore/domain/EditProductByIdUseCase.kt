package com.ndc.neostore.domain

import android.net.Uri
import com.ndc.neostore.data.repository.ProductRepository
import com.ndc.neostore.data.source.network.firebase.dto.ProductDto
import javax.inject.Inject

class EditProductByIdUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(
        productImage: Any,
        productDto: ProductDto,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        if (productImage is Uri) {
            productRepository.uploadProductImage(
                productId = productDto.productId,
                productImage = productImage,
                onSuccess = {
                    productRepository.editProductById(
                        updatedProduct = productDto.copy(productImageUrl = it),
                        onSuccess = {
                            onSuccess()
                        },
                        onFailure = onFailure
                    )
                },
                onFailure = onFailure
            )
        } else {
            productRepository.editProductById(
                updatedProduct = productDto.copy(productImageUrl = productImage.toString()),
                onSuccess = {
                    onSuccess()
                },
                onFailure = onFailure
            )
        }
    }
}