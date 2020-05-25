package com.example.myblog.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

import com.example.myblog.App
import com.example.myblog.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.layout_profile_item.*

class ProfileActivity: AppCompatActivity() {

    val TAG: String = "로그"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        camera_choice_button.setOnClickListener {
            Log.d(TAG, "ProfileActivity - 카메라 버튼이 클릭되었다.!")

            val items = arrayOf("카메라로 찍기", "앨범에서 고르기", "프로필 사진 삭제")

            AlertDialog.Builder(this)
                .setTitle("프로필 사진을 설정해 주세요!")
                .show()
        }

    }


}
