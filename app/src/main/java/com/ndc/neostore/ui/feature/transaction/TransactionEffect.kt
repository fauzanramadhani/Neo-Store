package com.ndc.neostore.ui.feature.transaction

sealed interface TransactionEffect {
    data object Empty : TransactionEffect
    data class OnShowToast(
        val message: String
    ) : TransactionEffect
}