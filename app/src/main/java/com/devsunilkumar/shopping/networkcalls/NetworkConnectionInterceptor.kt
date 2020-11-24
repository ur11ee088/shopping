package com.devsunilkumar.myzomato.networkcalls

import android.content.Context
import android.util.Log
import com.devsunilkumar.shopping.networkcalls.NoInternetException
import com.devsunilkumar.shopping.utils.Constants.NO_INTERNET
import com.devsunilkumar.shopping.utils.isInternetAvailable
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class NetworkConnectionInterceptor(context: Context) : Interceptor {
    private val applicationContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        val userAgent = System.getProperty("http.agent")

        if (!isInternetAvailable(applicationContext)) {
          //  Log.d("ExURL", chain.request().url().toString())
            throw NoInternetException(NO_INTERNET, "Make Sure you have an Active Data Connection")
        }
        val request: Request = chain.request()
            .newBuilder()
            .addHeader("user-key", "e18aff963dea667727e5b154d66323e9")
            .build()
        Log.e("usragent", "User:" + request)

        return chain.proceed(request)

        //return chain.proceed(chain.request())
    }



}