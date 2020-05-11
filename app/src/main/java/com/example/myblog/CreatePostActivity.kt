package com.example.myblog

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myblog.Interface.PostInterface
import com.example.myblog.retrofit.RetrofitManager
import kotlinx.android.synthetic.main.activity_create_post.*

class CreatePostActivity : AppCompatActivity() {

    val TAG: String = "로그"

    var postInterface: PostInterface? = null

    companion object {
        const val EXTRA_TASK_DESCRIPTION = "task"

//        fun newIntent(context: Context) = Intent(context, TaskDescriptionActivity::class.java)
//
//        fun getNewTask(data: Intent?): String? = data?.getStringExtra(TaskDescriptionActivity.EXTRA_TASK_DESCRIPTION)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)



        create_blog_post_button.setOnClickListener {

            Log.d(TAG, "CreatePostActivity / title.toString() : ${title_text.toString()}")

            RetrofitManager.instance.createBlogPost(title = title_text.text.toString(),
                                                    body = body_text.text.toString(),
                                                    completion = {

                                                        postInterface?.blogPostCreated()

                                                        // 1
                                                        val taskDescription = "hi"

                                                        if (!taskDescription.isEmpty()) {
                                                            // 2
                                                            val result = Intent()
                                                            result.putExtra("createPost", taskDescription)
                                                            setResult(Activity.RESULT_OK, result)
                                                        } else {
                                                            // 3
                                                            setResult(Activity.RESULT_CANCELED)
                                                        }

                                                        finish()

                                                    })
        }


    }
}
