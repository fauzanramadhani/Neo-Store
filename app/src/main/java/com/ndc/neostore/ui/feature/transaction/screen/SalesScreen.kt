package com.ndc.neostore.ui.feature.transaction.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ndc.neostore.R
import com.ndc.neostore.ui.component.card.TransactionCard
import com.ndc.neostore.ui.component.shimmer.shimmerBrush
import com.ndc.neostore.ui.feature.transaction.TransactionAction
import com.ndc.neostore.ui.feature.transaction.TransactionState
import com.ndc.neostore.ui.navigation.NavRoute

@Composable
fun SalesScreen(
    navHostController: NavHostController,
    listState: LazyListState,
    state: TransactionState,
    action: (TransactionAction) -> Unit,
) {
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        when {
            (state.loadingState) -> for (i in 1..3) item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(84.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(shimmerBrush())
                )
            }

            state.mySalesOrderList.isEmpty() -> item {
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
                        text = "Ups... Sepertinya Anda belum memiliki produk yang terjual",
                        style = typography.bodyMedium,
                        color = color.onBackground,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }

            else -> items(
                items = state.mySalesOrderList,
                key = { it.orderId }
            ) { mySalesProductDto ->
                TransactionCard(
                    userProfileImageUrl = mySalesProductDto.buyerProfileUrl,
                    username = mySalesProductDto.buyerName,
                    productImageUrl = mySalesProductDto.productImageUrl,
                    productName = mySalesProductDto.productName,
                    amount = mySalesProductDto.orderAmount,
                    totalPrice = (mySalesProductDto.productPrice * mySalesProductDto.orderAmount) + mySalesProductDto.adminFee,
                    status = mySalesProductDto.orderStatus
                ) {
                    navHostController.navigate(
                        NavRoute.DetailSalesScreen.navigateWithId(
                            mySalesProductDto.orderId
                        )
                    ) {
                        launchSingleTop = true
                    }
                }
            }
        }
    }
}