package com.example.myblog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.myblog.databinding.ActivityPostDetailBinding
import com.example.myblog.model.Post

class PostDetailActivity : AppCompatActivity() {


    private lateinit var binding: ActivityPostDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_post_detail)

        val post = intent.extras?.get("postItem") as Post

       val binding: ActivityPostDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_post_detail)

        binding.setVariable(BR.post, post)
        binding.executePendingBindings()
    }

//        if(post != null){
//            title_textview.setText(post.title)
//            body_textview.setText(post.body)
//        }

//    }


}
