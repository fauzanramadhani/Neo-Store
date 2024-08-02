package com.ndc.neostore.ui.feature.detailpurchase

import com.ndc.neostore.data.source.network.firebase.dto.MyPurchaseOrderDto

data class DetailPurchaseState(
    val loadingState: Boolean = false,
    val bottomSheetVisible: Boolean = false,
    val mySalesOrderDto: MyPurchaseOrderDto = MyPurchaseOrderDto()
)
