package com.ndc.neostore.ui.feature.dashboard.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.ndc.neostore.ui.component.card.MarketCard
import com.ndc.neostore.ui.feature.dashboard.DashboardAction
import com.ndc.neostore.ui.feature.dashboard.DashboardState
import com.ndc.neostore.ui.feature.dashboard.HomeBottomSheetType

@Composable
fun MarketScreen(
    paddingValues: PaddingValues,
    listState: LazyGridState,
    state: DashboardState,
    action: (DashboardAction) -> Unit,
) {
    LazyVerticalGrid(
        state = listState,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            start = 12.dp,
            end = 12.dp,
            top = paddingValues.calculateTopPadding() + 16.dp,
            bottom = paddingValues.calculateBottomPadding() + 16.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = state.marketProductDtoList,
            key = {
                it.productId
            }
        ) { product ->
            MarketCard(
                productName = product.productName,
                productImage = product.productImageUrl,
                price = product.productPrice,
                sellerImage = product.sellerProfileUrl,
                sellerName = product.sellerName
            ) {
                action(DashboardAction.OnBottomSheetVisibilityChange(
                    visible = true,
                    type = HomeBottomSheetType.MarketProductDetail(product)
                ))
            }
        }
    }
}