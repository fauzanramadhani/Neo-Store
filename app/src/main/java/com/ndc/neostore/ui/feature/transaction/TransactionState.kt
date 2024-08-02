package com.ndc.neostore.ui.feature.transaction

import com.ndc.neostore.data.source.network.firebase.dto.MyPurchaseOrderDto
import com.ndc.neostore.data.source.network.firebase.dto.MySalesOrderDto

data class TransactionState(
    val currentScreen: Int = 0,
    val loadingState: Boolean = false,
    val myPurchaseOrderList: List<MyPurchaseOrderDto> = emptyList(),
    val mySalesOrderList: List<MySalesOrderDto> = emptyList(),
)
