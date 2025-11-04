package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LogIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val signUpTextView = findViewById<TextView>(R.id.signup)
        signUpTextView.setOnClickListener {
            val intent = Intent(this@LogIn, SignUp::class.java)
            startActivity(intent)
        }
    }
}
