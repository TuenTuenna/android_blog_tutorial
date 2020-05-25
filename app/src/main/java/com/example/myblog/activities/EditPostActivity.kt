package com.example.myblog.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.example.myblog.R
import com.example.myblog.model.Post
import com.example.myblog.retrofit.RetrofitManager
import kotlinx.android.synthetic.main.activity_edit_post.*

class EditPostActivity : AppCompatActivity() {

    val TAG: String = "로그"

    private lateinit var post: Post

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_post)

        Log.d(TAG, "EditPostActivity - onCreate() called")


//        post = intent.extras?.get("postItem") as Post
        post = intent.getSerializableExtra("postItem") as Post



        Log.d(TAG, "EditPostActivity - onCreate() called / post.id : ${post.id}")

        title_text.setText(post.title)
        body_text.setText(post.body)

//        val postToSend = Post(post.id, title_text.text.toString(), body_text.text.toString())
//
//        Log.d(TAG, "EditPostActivity - postToSend.id : ${postToSend.id}")
//        Log.d(TAG, "EditPostActivity - postToSend.title : ${postToSend.title}")
//        Log.d(TAG, "EditPostActivity - postToSend.body : ${postToSend.body}")

        title_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(title_text.text.toString() == ""){
                    Log.d(TAG, "EditPostActivity - 타이틀 비었음")
                    title_text_input_layout.error = "입력하시오"

                    edit_blog_post_button.isEnabled = false

                } else {
                    Log.d(TAG, "EditPostActivity - title_text.text.toString() : ${title_text.text.toString()}")
                    title_text_input_layout.error = null

                    if(body_text.text.toString() != ""){
                        edit_blog_post_button.isEnabled = true
                    }


                }

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        })


        body_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(body_text.text.toString() == ""){
                    Log.d(TAG, "EditPostActivity - 바디 비었음")
                    body_text_input_layout.error = "입력하시오"

                    edit_blog_post_button.isEnabled = false

                } else {
                    Log.d(TAG, "EditPostActivity - title_text.text.toString() : ${title_text.text.toString()}")
                    body_text_input_layout.error = null

                    if(title_text.text.toString() != ""){
                        edit_blog_post_button.isEnabled = true
                    }

                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        })



        if(title_text.length() > 0 && body_text.length() > 0){
            edit_blog_post_button.isEnabled = true
        } else {
            edit_blog_post_button.isEnabled = false
        }


        // 수정완료 버튼이 클릭 되었을때
        edit_blog_post_button.setOnClickListener {
                RetrofitManager.instance.editBlogPost(  post_id = post.id.toString(),
                                                        title = title_text.text.toString(),
                                                        body = body_text.text.toString(),
                                                        completion = {

                                                            val result = Intent()
                                                            result.putExtra("editedPost", it)
                                                            setResult(Activity.RESULT_OK, result)

                                                            this.finish()
                                                        }
                                                    )
        }

    }

}
