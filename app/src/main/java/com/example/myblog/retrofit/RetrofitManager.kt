package com.example.myblog.retrofit

import android.util.Log
import com.example.myblog.model.Post
import com.example.myblog.utils.Constants.API_BASE_URL
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response

class RetrofitManager {


    val TAG: String = "로그"

    companion object {

        val instance = RetrofitManager()

    }

    private val httpCall : ApiService? = RetrofitClient.getClient(API_BASE_URL)?.create(ApiService::class.java)

    //
    fun getPostsPaginate(page: Int, completion: (ArrayList<Post>) -> Unit){

       val call = httpCall?.getPosts(page = page)
       call?.enqueue(object : retrofit2.Callback<JsonElement>{
           override fun onFailure(call: Call<JsonElement>, t: Throwable) {
               Log.d(TAG, "RetrofitManager - onFailure() called : t : ${t}")
           }

           override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
               Log.d(TAG, "response.body : ${response.body()} / page: $page")

               response.body()?.let {


                   val jsonObject = it.asJsonObject

                   val fetchedPostsJsonArray = jsonObject.get("data").asJsonArray

                   // 배열을 만든다.
                   var postArrayList = ArrayList<Post>()

                   fetchedPostsJsonArray.forEach {
                       var jsonObjectItem = it.asJsonObject

                       val postItem = Post(title = jsonObjectItem.get("title").asString,
                                            body = jsonObjectItem.get("body").asString
                                            )

                       postArrayList.add(postItem)
                   }

//                   Log.d(TAG, "RetrofitManager - onResponse() called / 배열크기 : ${postArrayList.size}")
                    completion(postArrayList)
               }

           }

       })

    }

    // 포스팅 하기
    fun createBlogPost(title: String, body: String, completion: () -> Unit){

        Log.d(TAG, "RetrofitManager - createBlogPost() called / title : $title / body: $body")

        val call = httpCall?.createPost(title, body)
        call?.enqueue(object : retrofit2.Callback<JsonElement>{
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG, "RetrofitManager - onFailure() called : t : ${t}")

            }

            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "response.body : ${response.body()}")

                completion()

            }

        })
    }



}
