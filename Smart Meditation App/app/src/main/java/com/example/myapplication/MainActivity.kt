package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainpage1)

        // Delay for 3 seconds before transitioning to LogIn
        handler.postDelayed({
                val intent = Intent(this@MainActivity, LogIn::class.java)
        startActivity(intent)
        finish() // Optional: Call finish() to prevent returning to MainActivity
        }, 1000) // 1000 milliseconds delay
    }
}