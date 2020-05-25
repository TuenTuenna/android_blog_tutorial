package com.example.myblog.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myblog.App
import com.example.myblog.Interface.RecyclerViewItemInterface
import com.example.myblog.activities.PostDetailActivity
import com.example.myblog.R
import com.example.myblog.model.Post

class PostsListAdapter(postItemInterface: RecyclerViewItemInterface) : RecyclerView.Adapter<PostViewHolder>() {

    val TAG: String = "로그"

    private var postsList : ArrayList<Post> = ArrayList()

    var postItemInterface: RecyclerViewItemInterface? = null

    init {
        this.postItemInterface = postItemInterface
    }

    companion object {

        private const val TYPE_LIST = 0
        private const val TYPE_EMPTY = 1

    }


    // 뷰홀더가 메모리에 올라갔을때
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {

        return when (viewType) {
            TYPE_LIST -> PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_post_item, parent, false), this.postItemInterface!!)

            TYPE_EMPTY -> PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_empty_post_item, parent, false), this.postItemInterface!!)

            else -> PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_post_item, parent, false), this.postItemInterface!!)
        }

    }

    // 아이템에 대한 타입을 정한다.
    override fun getItemViewType(position: Int): Int {

        if(postsList[position].isFetchedAll == true){

            return TYPE_EMPTY

        } else {

            return TYPE_LIST
        }

    }

    // 보여줄 목록의 갯수
    override fun getItemCount(): Int {
        return postsList.size
    }



    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {

        if (postsList.size > 0){
            // 데이터와 뷰를 묶어준다.
            holder.bind(postsList[position])
        }


    }



    // 즉 총알을 넣는다.
    fun submitList(postsList: ArrayList<Post>) {
        this.postsList = postsList
    }

    fun addList(postsList: ArrayList<Post>){
        this.postsList.addAll(postsList)
    }

    fun addEmptyPost(emptyPost: Post){
        this.postsList.add(emptyPost)
    }

    // 비운다.
    fun clearList() {
        this.postsList.clear()
    }


}
