package com.example.MyBook.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.example.MyBook.R

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT:Long = 5000 // 3 sec
    lateinit var imageView_splash : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_splash)
        setTheme(R.style.AppTheme)
        val img : ImageView = findViewById<ImageView>(R.id.imageView_splash)
        Glide.with(img.context).load("https://images.qdq.com/uGxYUNcP-vX3-Q4sJZVWEDwor_Q=/310x175/smart/photos/049/049070977/3fb1eea9f75047179abe1f7fd5b579ab.jpg").into(img)
        Handler().postDelayed({
            startActivity(Intent(this, LoginActivity
                ::class.java))
            finish()
        }, SPLASH_TIME_OUT)
    }

}