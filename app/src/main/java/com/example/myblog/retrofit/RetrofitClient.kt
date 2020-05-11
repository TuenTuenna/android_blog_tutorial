package com.example.myblog.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {

    val TAG: String = "로그"

    // 레트로핏 클라이언트 선언

    private var retrofitClient: Retrofit? = null


    // 레트로핏 클라이언트 가져오기
    fun getClient(baseUrl: String): Retrofit? {

        // 만약 레트로핏 클라이이언트가 없으면
        if(retrofitClient == null){

            val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
//                this.level = HttpLoggingInterceptor.Level.BODY
                this.level = HttpLoggingInterceptor.Level.BASIC
            }

            val client : OkHttpClient = OkHttpClient.Builder().apply {
                this.addInterceptor(interceptor)
            }.build()

            // 인스턴스 생성
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        return retrofitClient

    }

}
