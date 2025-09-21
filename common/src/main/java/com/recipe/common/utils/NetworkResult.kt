package com.recipe.common.utils

sealed class NetworkResult<T>(
    val data: T? = null,
    val errorMessage: String? = null,
    val errorCode: Int? = null
) {
    class Success<T>(data: T?) : NetworkResult<T>(data)
    class Error<T>(errorMessage: String?, errorCode: Int? = null, data: T? = null) :
        NetworkResult<T>(data, errorMessage, errorCode)

    class Loading<T>(data: T? = null) : NetworkResult<T>(data)
}