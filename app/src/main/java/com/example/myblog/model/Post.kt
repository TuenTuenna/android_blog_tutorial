package com.example.myblog.model

import android.os.Parcel
import android.os.Parcelable

class Post() : Parcelable {

    var title: String = ""
    var body: String = ""

    var isFetchedAll: Boolean = false

    constructor(parcel: Parcel) : this() {
        title = parcel.readString().toString()
        body = parcel.readString().toString()
        isFetchedAll = parcel.readByte() != 0.toByte()
    }

    constructor(title: String, body: String) : this() {
        this.title = title
        this.body = body
    }

    constructor(isFetchedAll: Boolean) : this(){
        this.isFetchedAll = isFetchedAll
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(body)
        parcel.writeByte(if (isFetchedAll) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
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
