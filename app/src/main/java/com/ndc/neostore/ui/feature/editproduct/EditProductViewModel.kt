package com.ndc.neostore.ui.feature.editproduct

import com.ndc.neostore.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProductViewModel @Inject constructor(

) : BaseViewModel<EditProductState, EditProductAction, EditProductEffect>(
    EditProductState()
) {
    override fun onAction(action: EditProductAction) {
        TODO("Not yet implemented")
    }

}