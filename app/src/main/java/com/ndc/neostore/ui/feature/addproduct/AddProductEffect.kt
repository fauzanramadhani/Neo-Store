package com.ndc.neostore.ui.feature.addproduct

sealed interface AddProductEffect {
    data object Empty : AddProductEffect
    data object OnSuccessAddProduct : AddProductEffect
}