package com.devsunilkumar.shopping.networkcalls
import com.devsunilkumar.myzomato.networkcalls.NetworkConnectionInterceptor
import com.devsunilkumar.shopping.BuildConfig
import com.devsunilkumar.shopping.model.categories.CategoryResponse
import com.devsunilkumar.shopping.model.sub_categories_products.SubCategoriesResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

@JvmSuppressWildcards
interface MyShopApiCallsInterface {



    @GET("5e16d5263000002a00d5616c")
    suspend fun getListOfCategories(): Response<CategoryResponse>

    @GET("5e16d5443000004e00d5616d")
    suspend fun getlistOfSubcategoires(): Response<SubCategoriesResponse>


    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): MyShopApiCallsInterface {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url
                    .newBuilder()
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }


            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                    else HttpLoggingInterceptor.Level.NONE


            }

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(requestInterceptor)
                .connectTimeout(300, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .writeTimeout(300, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okkHttpclient)
                .build()
                .create(MyShopApiCallsInterface::class.java)
        }
    }
}