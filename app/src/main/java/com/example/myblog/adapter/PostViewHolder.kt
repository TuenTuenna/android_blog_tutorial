package com.example.myblog.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myblog.model.Post
import kotlinx.android.synthetic.main.layout_post_item.view.*



class PostViewHolder

    constructor( itemView: View) : RecyclerView.ViewHolder(itemView){

    //
    val itemTitle = itemView.title_textview
    val itemBody = itemView.body_textview

        // 데이터와 뷰를 묶는다. 즉 포스트 모델 클래스랑 뷰의 요소를 일치 시킴
        fun bind(postItem: Post){

            if(postItem.isFetchedAll == false) {
                itemTitle.setText(postItem.title)
                itemBody.setText(postItem.body)
            }

        }

    }


