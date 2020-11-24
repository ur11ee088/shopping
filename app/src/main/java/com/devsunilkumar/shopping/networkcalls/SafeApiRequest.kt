package com.devsunilkumar.shopping.networkcalls

import android.util.Log
import retrofit2.Response

abstract class SafeApiRequest {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val error = response.errorBody()?.string().toString()
            Log.d("ErrorCode", response.code().toString())
            Log.d("ErrorMessage", error)
            throw ApiException(response.code(), error)
        }
    }
}

