package com.example.MyBook.Activities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.MyBook.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setTheme(R.style.AppTheme)
    }
}