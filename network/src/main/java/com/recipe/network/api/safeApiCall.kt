package com.recipe.network.api

import com.recipe.common.utils.NetworkResult
import com.recipe.network.utils.CONNECT_EXCEPTION
import com.recipe.network.utils.SOCKET_TIME_OUT_EXCEPTION
import com.recipe.network.utils.UNKNOWN_HOST_EXCEPTION
import com.recipe.network.utils.UNKNOWN_NETWORK_EXCEPTION
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


suspend fun <T : Any> safeApiCall(
    call: suspend () -> Response<T>,
): NetworkResult<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = call()
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    NetworkResult.Success(body)
                } ?: NetworkResult.Error("Response body is null", response.code())
            } else {
                NetworkResult.Error(response.message(), response.code())
            }
        } catch (e: Exception) {
            when (e) {
                is ConnectException -> NetworkResult.Error(CONNECT_EXCEPTION)
                is UnknownHostException -> NetworkResult.Error(UNKNOWN_HOST_EXCEPTION)
                is SocketTimeoutException -> NetworkResult.Error(SOCKET_TIME_OUT_EXCEPTION)
                is HttpException -> NetworkResult.Error("HTTP error: ${e.code()}", e.code())
                else -> NetworkResult.Error(e.message ?: UNKNOWN_NETWORK_EXCEPTION)
            }
        }
    }
}