package com.example.emptycomposeactivity.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class Interceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request: Request = chain.request().newBuilder().apply {
            addHeader("accept", "application/json")
            addHeader("content-Type", "application/x-www-form-urlencoded")
            Network.token?.let{
                addHeader("Authorization", "Bearer ${ it.token }")
            }
        }.build()

        var response: Response? = null

        return try{
            response = chain.proceed(request)
            response
        } catch (e: Exception) {
            response?.close()
            chain.proceed(request)
        }
    }
}