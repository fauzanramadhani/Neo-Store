package com.ndc.neostore.ui.feature.detailsales

import com.ndc.neostore.data.source.network.firebase.dto.MySalesOrderDto

data class DetailSalesState(
    val loadingState: Boolean = false,
    val bottomSheetVisible: Boolean = false,
    val mySalesOrderDto: MySalesOrderDto = MySalesOrderDto()
)
