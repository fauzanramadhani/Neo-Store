package com.ndc.neostore.ui.feature.editproduct

import androidx.lifecycle.viewModelScope
import com.ndc.neostore.base.BaseViewModel
import com.ndc.neostore.domain.EditProductByIdUseCase
import com.ndc.neostore.domain.GetProductByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProductViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val editProductByIdUseCase: EditProductByIdUseCase
) : BaseViewModel<EditProductState, EditProductAction, EditProductEffect>(
    EditProductState()
) {
    override fun onAction(action: EditProductAction) {
        when (action) {
            EditProductAction.OnEditProduct -> editProductById()
            is EditProductAction.OnImageProductChange -> updateState { copy(productImage = action.imageUri) }
            is EditProductAction.OnProductDescriptionValueChange -> updateState {
                copy(
                    productDescriptionValue = action.value
                )
            }

            is EditProductAction.OnProductNameValueChange -> updateState { copy(productNameValue = action.value) }
            is EditProductAction.OnProductPriceValueChange -> updateState { copy(productPriceValue = action.value) }
            is EditProductAction.OnProductStockValueChange -> updateState { copy(productStockValue = action.value) }
            is EditProductAction.OnGetProduct -> getProductById(action.productId)
        }
    }

    private fun getProductById(productId: String) = viewModelScope.launch {
        updateState { copy(loadingState = true) }
        getProductByIdUseCase.invoke(
            productId = productId,
            onSuccess = {
                updateState {
                    copy(
                        loadingState = false,
                        productImage = it.productImageUrl,
                        productNameValue = it.productName,
                        productDescriptionValue = it.productDescription,
                        productPriceValue = it.productPrice.toString(),
                        productStockValue = it.productStock.toString(),
                        productDto = it
                    )
                }
            },
            onFailure = {
                onShowToast(it)
            }
        )
    }

    private fun editProductById() = viewModelScope.launch {
        updateState { copy(loadingState = true) }
        with(state.value) {
            editProductByIdUseCase.invoke(
                productImage = productImage,
                productDto = productDto.copy(
                    productName = productNameValue,
                    productDescription = productDescriptionValue,
                    productPrice = productPriceValue.toLong(),
                    productStock = productStockValue.toInt(),
                ),
                onSuccess = {
                    onSuccessEditProduct()
                },
                onFailure = {
                    onShowToast(it)
                }
            )
        }
    }

    private fun onShowToast(message: String) = viewModelScope.launch {
        updateState { copy(loadingState = false) }
        sendEffect(EditProductEffect.OnShowToast(message))
        delay(1000)
        sendEffect(EditProductEffect.Empty)
    }

    private fun onSuccessEditProduct() = viewModelScope.launch {
        updateState { copy(loadingState = false) }
        sendEffect(EditProductEffect.OnSuccessEditProduct)
        delay(1000)
        sendEffect(EditProductEffect.Empty)
    }

}