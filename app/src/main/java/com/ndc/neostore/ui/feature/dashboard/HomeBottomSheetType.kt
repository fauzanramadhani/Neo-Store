package com.ndc.neostore.ui.feature.dashboard

import com.ndc.neostore.data.source.network.firebase.dto.MarketProductDto


sealed interface HomeBottomSheetType {
    data object NotReady : HomeBottomSheetType
    data class MarketProductDetail(
        val marketProductDto: MarketProductDto
    ) : HomeBottomSheetType
}