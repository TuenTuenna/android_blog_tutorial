package com.example.myblog.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.myblog.MainActivity
import com.example.myblog.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    val TAG: String = "로그"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Log.d(TAG, "SplashActivity - onCreate() called")


//        Handler().postDelayed({
//
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//
//        }, 2000)


        GlobalScope.launch(context = Dispatchers.Main) {

            delay(2000)

            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)

        }





    }

}
