package com.ndc.neostore.ui.feature.editproduct

sealed interface EditProductEffect {
    data object Empty: EditProductEffect
}