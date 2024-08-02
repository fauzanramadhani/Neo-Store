package com.ndc.neostore.ui.feature.dashboard.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ndc.neostore.ui.component.card.MyProductCard
import com.ndc.neostore.ui.feature.dashboard.DashboardAction
import com.ndc.neostore.ui.feature.dashboard.DashboardState
import com.ndc.neostore.ui.navigation.NavRoute

@Composable
fun MyStoreScreen(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    listState: LazyListState,
    state: DashboardState,
    action: (DashboardAction) -> Unit,
) {
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(
            start = 12.dp,
            end = 12.dp,
            top = paddingValues.calculateTopPadding() + 16.dp,
            bottom = paddingValues.calculateBottomPadding() + 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = state.myProductList,
            key = {
                it.productId
            }
        ) {
            MyProductCard(
                productName = it.productName,
                productImage = it.productImageUrl,
                stock = it.productStock,
                price = it.productPrice
            ) {
                navHostController.navigate(NavRoute.EditProductScreen.navigateWithId(it.productId)) {
                    launchSingleTop = true
                }
            }
        }
    }
}