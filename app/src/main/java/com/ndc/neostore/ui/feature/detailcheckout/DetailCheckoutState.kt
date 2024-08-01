package com.ndc.neostore.ui.feature.detailcheckout

import com.ndc.neostore.data.source.network.firebase.dto.MarketProductDto

data class DetailCheckoutState(
    val loadingState: Boolean = false,
    val marketProductDto: MarketProductDto = MarketProductDto(),
    val adminFee: Long = 2000L,
    val buyAmount: Int = 0,
    val buyAmountErrorState: Boolean = false,
    val paymentMethod: String = "Neo Pay"
)
