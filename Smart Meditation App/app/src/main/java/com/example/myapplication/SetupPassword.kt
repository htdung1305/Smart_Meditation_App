package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SetupPassword : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup_password)

        val buttonNext: Button = findViewById(R.id.next_button)
        buttonNext.setOnClickListener {
            val intent = Intent(this@SetupPassword, Scanface::class.java)
            startActivity(intent)
        }
    }
}

