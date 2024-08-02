package com.ndc.neostore.ui.feature.detailpurchase

import com.ndc.neostore.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailPurchaseViewModel @Inject constructor(

): BaseViewModel<DetailPurchaseState, DetailPurchaseAction, DetailPurchaseEffect>(
    DetailPurchaseState()
) {
    override fun onAction(action: DetailPurchaseAction) {

    }
}