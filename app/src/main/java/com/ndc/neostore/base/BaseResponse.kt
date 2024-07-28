package com.ndc.neostore.base

sealed interface BaseResponse<T> {
    data class OnSuccess<T>(val data: T) : BaseResponse<T>
    data class OnError(val exception: Exception) : BaseResponse<Nothing>
    data object OnLoading : BaseResponse<Nothing>
}