package com.example.vku_decuong_2.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    var BASE_URL: String = "http://192.168.1.4/vku/"
    val getClient: ApiService
        get() {
            val gson = GsonBuilder().setLenient().create()
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(
                    GsonConverterFactory.create(gson)).build()
            return retrofit.create(ApiService::class.java)
        }

}