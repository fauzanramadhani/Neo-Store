package com.ndc.neostore.ui.feature.detailpurchase

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun DetailPurchaseScreen(
    navHostController: NavHostController,
    state: DetailPurchaseState,
    effect: DetailPurchaseEffect,
    action: (DetailPurchaseAction) -> Unit,
    productId: String = ""
) {

}