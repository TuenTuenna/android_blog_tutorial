package com.example.myblog.adapter

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myblog.Interface.RecyclerViewItemInterface
import com.example.myblog.model.Post
import kotlinx.android.synthetic.main.layout_post_item.view.*



class PostViewHolder(itemView: View, itemInterface: RecyclerViewItemInterface) : RecyclerView.ViewHolder(itemView), View.OnClickListener {


    val TAG: String = "로그"

    //
    private val itemTitle = itemView.title_textview
    private val itemBody = itemView.body_textview

    private var itemInterface: RecyclerViewItemInterface? = null


    init {

        itemView.setOnClickListener(this)
        this.itemInterface = itemInterface

    }

    // 데이터와 뷰를 묶는다. 즉 포스트 모델 클래스랑 뷰의 요소를 일치 시킴
    fun bind(postItem: Post){
        Log.d(TAG, "PostViewHolder - bind() called")
        if(!postItem.isFetchedAll) {
            itemTitle.text = postItem.title
            itemBody.text = postItem.body
        }
    }

    // 뷰가 클릭되었을때
    override fun onClick(p0: View?) {
        Log.d(TAG, "PostViewHolder - onClick() called")
        this.itemInterface!!.itemClicked(adapterPosition)
    }

}


