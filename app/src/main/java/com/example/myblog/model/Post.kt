package com.example.myblog.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class Post() : Serializable {

    var title: String = ""
    var body: String = ""
    var id: Int = 0

    var isFetchedAll: Boolean = false

    constructor(parcel: Parcel) : this() {
        title = parcel.readString().toString()
        body = parcel.readString().toString()
        isFetchedAll = parcel.readByte() != 0.toByte()
    }

    constructor(id: Int, title: String, body: String) : this() {
        this.id = id
        this.title = title
        this.body = body
    }

    constructor(isFetchedAll: Boolean) : this(){
        this.isFetchedAll = isFetchedAll
    }



    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }


}
