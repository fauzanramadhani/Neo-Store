package com.ndc.neostore.ui.feature.addproduct

import androidx.lifecycle.viewModelScope
import com.ndc.neostore.base.BaseViewModel
import com.ndc.neostore.domain.AddProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val addProductUseCase: AddProductUseCase
) : BaseViewModel<AddProductState, AddProductAction, AddProductEffect>(
    AddProductState()
) {
    override fun onAction(action: AddProductAction) {
        when (action) {
            is AddProductAction.OnImageProductChange -> updateState { copy(productImageUri = action.imageUri) }
            is AddProductAction.OnProductDescriptionValueChange -> updateState {
                copy(
                    productDescriptionValue = action.value
                )
            }

            is AddProductAction.OnProductNameValueChange -> updateState { copy(productNameValue = action.value) }
            is AddProductAction.OnProductPriceValueChange -> updateState { copy(productPriceValue = action.value) }
            is AddProductAction.OnProductStockValueChange -> updateState { copy(productStockValue = action.value) }
            AddProductAction.OnAddProduct -> addProduct()
        }
    }

    private fun addProduct() = viewModelScope.launch {
        updateState { copy(loadingState = true) }
        with(state.value) {
            productImageUri?.let { uri ->
                addProductUseCase.invoke(
                    productImage = uri,
                    productName = productNameValue,
                    productDescription = productDescriptionValue,
                    productPrice = productPriceValue.toLong(),
                    productStock = productStockValue.toInt(),
                    onSuccess = { _ ->
                        updateState { copy(loadingState = false) }
                        onSuccessAddProduct()
                    },
                    onFailure = {
                        updateState { copy(loadingState = false) }
                    }
                )
            }
        }
    }

    private fun onSuccessAddProduct() = viewModelScope.launch {
        sendEffect(AddProductEffect.OnSuccessAddProduct)
        delay(1000)
        sendEffect(AddProductEffect.Empty)
    }
}