package com.polije.githubusersapps.Services


import com.polije.githubusersapps.BuildConfig
import com.polije.githubusersapps.Services.RetrofitServices.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitServices {

    private val client = OkHttpClient.Builder().apply {

        addInterceptor { chain ->
            val request: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + BuildConfig.API_TOKEN)
                .build()
            chain.proceed(request)
        }
    }.build()

    const val BASE_URL = BuildConfig.BASE_URL
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <T> buildSvc(service: Class<T>): T {
        return retrofit.create(service)
    }
}