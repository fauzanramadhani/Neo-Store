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
import com.ndc.neostore.ui.component.card.TransactionCard
import com.ndc.neostore.ui.component.shimmer.shimmerBrush
import com.ndc.neostore.ui.feature.transaction.TransactionAction
import com.ndc.neostore.ui.feature.transaction.TransactionState

@Composable
fun SalesScreen(
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
                )
            }
        }
    }
}