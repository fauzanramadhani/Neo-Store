package com.ndc.neostore.ui.feature.addproduct

import com.ndc.neostore.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(

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
        }
    }

}