package com.ndc.neostore.ui.feature.editproduct

sealed interface EditProductEffect {
    data object Empty : EditProductEffect
    data object OnSuccessEditProduct : EditProductEffect
    data class OnShowToast(
        val message: String
    ): EditProductEffect
}