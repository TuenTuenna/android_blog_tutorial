package com.example.myblog.adapter

import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("setPostText")
fun setPostText(textView: TextView, textValue: String) {
    if(textValue != null){
        textView.setText(textValue)
    }
    Log.d("로그", "BindingAdapter - setPostText() called")
}
