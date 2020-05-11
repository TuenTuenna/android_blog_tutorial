package com.example.myblog.retrofit

import com.example.myblog.utils.Constants.API_GET_USERS
import com.example.myblog.utils.Constants.API_POST_BLOGPOST
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET(API_GET_USERS)
    fun getPosts(@Query("page") page: Int): Call<JsonElement>

    @FormUrlEncoded
    @POST(API_POST_BLOGPOST)
    fun createPost(@Field("title") title: String, @Field("body") body: String): Call<JsonElement>


}
