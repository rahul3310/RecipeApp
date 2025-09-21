package com.recipe.network.interceptor

import com.recipe.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url
        val newUrl = url.newBuilder()
            .addQueryParameter("apiKey", BuildConfig.RECIPE_API_KEY)
            .build()
        return chain.proceed(request.newBuilder().url(newUrl).build())
    }
}