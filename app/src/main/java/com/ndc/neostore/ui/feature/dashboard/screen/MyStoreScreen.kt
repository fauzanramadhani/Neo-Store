package com.ndc.neostore.ui.feature.dashboard.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ndc.neostore.R
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
        when {
            state.myProductList.isEmpty() -> item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.empty_illustration),
                        contentDescription = ""
                    )
                    Text(
                        text = "Ups... Sepertinya Anda belum memiliki produk",
                        style = typography.bodyMedium,
                        color = color.onBackground,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
            else -> items(
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
}