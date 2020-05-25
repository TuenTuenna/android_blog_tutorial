package com.example.myblog.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import com.example.myblog.Interface.PostInterface
import com.example.myblog.R
import com.example.myblog.retrofit.RetrofitManager
import com.google.android.material.datepicker.MaterialDatePicker
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


//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

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

    // 상단 메뉴 아이콘 설정
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        Log.d(TAG, "CreatePostActivity - onCreateOptionsMenu() called")

        val inflater = menuInflater
        inflater.inflate(R.menu.post_create_menu, menu)

        return true
    }


    // actions on click menu items
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.menu_post_date -> {
            Log.d(TAG, "MainActivity - 메뉴 날짜 클릭")


            val builder = MaterialDatePicker.Builder.datePicker()

            val picker = builder.build()

            picker.show(supportFragmentManager, picker.toString())

            picker.addOnCancelListener {
                Log.d(TAG, "달력 취소!")
            }

            picker.addOnPositiveButtonClickListener {
                Log.d(TAG, "Date String = ${picker.headerText}::  Date epoch values::${it}")


                post_pending_date.visibility = View.VISIBLE
                post_pending_date.text = "자동 포스팅 예정일: \n" + picker.headerText
            }

//            val intent = Intent(this, CreatePostActivity::class.java).apply {
//
//            }

//            startActivityForResult(intent, CREATE_POST_ACTIVITY)

            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }













}
