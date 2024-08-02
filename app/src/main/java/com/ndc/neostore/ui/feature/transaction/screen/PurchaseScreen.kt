package com.ndc.neostore.ui.feature.transaction.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ndc.neostore.ui.component.card.TransactionCard
import com.ndc.neostore.ui.component.shimmer.shimmerBrush
import com.ndc.neostore.ui.feature.transaction.TransactionAction
import com.ndc.neostore.ui.feature.transaction.TransactionState
import com.ndc.neostore.ui.navigation.NavRoute

@Composable
fun PurchaseScreen(
    navHostController: NavHostController,
    listState: LazyListState,
    state: TransactionState,
    action: (TransactionAction) -> Unit,
) {
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

            else -> items(
                items = state.myPurchaseOrderList,
                key = { it.orderId }
            ) { myPurchaseOrderDto ->
                TransactionCard(
                    userProfileImageUrl = myPurchaseOrderDto.sellerProfileUrl,
                    username = myPurchaseOrderDto.sellerName,
                    productImageUrl = myPurchaseOrderDto.productImageUrl,
                    productName = myPurchaseOrderDto.productName,
                    amount = myPurchaseOrderDto.orderAmount,
                    totalPrice = (myPurchaseOrderDto.productPrice * myPurchaseOrderDto.orderAmount) + myPurchaseOrderDto.adminFee,
                    status = myPurchaseOrderDto.orderStatus
                ) {
                    navHostController.navigate(
                        NavRoute.DetailPurchaseScreen.navigateWithId(
                            myPurchaseOrderDto.orderId
                        )
                    ) {
                        launchSingleTop = true
                    }
                }
            }
        }

    }
}