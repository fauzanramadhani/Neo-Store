package com.ndc.neostore.ui.feature.dashboard

import com.ndc.neostore.data.source.network.firebase.dto.MarketProductDto
import com.ndc.neostore.data.source.network.firebase.dto.ProductDto
import com.ndc.neostore.data.source.network.firebase.dto.UserDto

data class DashboardState(
    val currentScreen: Int = 0,
    val bottomSheetVisible: Boolean = false,
    val homeBottomSheetType: HomeBottomSheetType = HomeBottomSheetType.NotReady,
    // Market Screen
    val marketProductDtoList: List<MarketProductDto> = emptyList(),
    // My Store Screen
    val myProductList: List<ProductDto> = emptyList(),
    // Account Screen
    val userDto: UserDto = UserDto()
)
