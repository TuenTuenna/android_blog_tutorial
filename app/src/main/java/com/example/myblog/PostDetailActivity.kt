package com.example.myblog

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.example.myblog.databinding.ActivityPostDetailBinding
import com.example.myblog.model.Post
import com.example.myblog.utils.Constants.EDIT_POST_ACTIVITY
import kotlinx.android.synthetic.main.activity_post_detail.*

class PostDetailActivity : AppCompatActivity() {


    val TAG: String = "로그"

    private lateinit var binding: ActivityPostDetailBinding

    private lateinit var post : Post

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setContentView(R.layout.activity_post_detail)

        post = intent.extras?.get("postItem") as Post

        Log.d(TAG, "PostDetailActivity - onCreate() called / post.id : ${post.id}")
        Log.d(TAG, "PostDetailActivity - onCreate() called / post.title : ${post.title}")
        Log.d(TAG, "PostDetailActivity - onCreate() called / post.body : ${post.body}")

        val binding: ActivityPostDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_post_detail)

        binding.setVariable(BR.post, post)

        binding.executePendingBindings()
    }

    // 메뉴 옵션이 생성될때
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.post_detail_menu, menu)

        return true
    }

    // actions on click menu items
    // 메뉴 옵션아이템이 선택되었을때
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.menu_edit_post -> {
            Log.d(TAG, "PostDetailActivity - 메뉴 포스팅 수정 버튼 클릭")

            val intent = Intent(this, EditPostActivity::class.java)

            intent.putExtra("postItem", this.post)



            startActivityForResult(intent, EDIT_POST_ACTIVITY)

            true
        }
        R.id.menu_delete_post -> {
            Log.d(TAG, "PostDetailActivity - 메뉴 포스팅 삭제 버튼 클릭")
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d(TAG, "PostDetailActivity - onActivityResult() called")

        Log.d(TAG, "PostDetailActivity - requestCode : $requestCode")

        if(resultCode == Activity.RESULT_OK){

            when(requestCode) {

                EDIT_POST_ACTIVITY -> {

                    Log.d(TAG, "PostDetailActivity - onActivityResult() called / 포스팅을 수정하였다")
//                    binding.setVariable(BR.post, post)
                    data.let {

                         val receivedPost = it?.getSerializableExtra("editedPost") as Post
                        Log.d(TAG, "PostDetailActivity - onActivityResult() called / receivedPost.title body: ${receivedPost.title}")
                        Log.d(TAG, "PostDetailActivity - onActivityResult() called / receivedPost.body : ${receivedPost.body}")
                        title_textview.text = receivedPost.title as String
                        body_textview.text = receivedPost.body as String
                    }
                }

            }

        }

    }


}
