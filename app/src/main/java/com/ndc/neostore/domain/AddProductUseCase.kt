package com.ndc.neostore.domain

import android.net.Uri
import com.ndc.neostore.data.repository.ProductRepository
import com.ndc.neostore.utils.generateRandomId
import javax.inject.Inject

class AddProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    private val productId = generateRandomId("PRODUCT")

    operator fun invoke(
        productImage: Uri,
        productName: String,
        productDescription: String,
        productPrice: Long,
        productStock: Int,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) = productRepository.uploadProductImage(
        productId = productId,
        productImage = productImage,
        onSuccess = {
            productRepository.addProduct(
                productId = productId,
                productImageUrl = it,
                productName = productName,
                productDescription = productDescription,
                productPrice = productPrice,
                productStock = productStock,
                onSuccess = {
                    onSuccess(productId)
                },
                onFailure = onFailure
            )
        },
        onFailure = onFailure
    )
}