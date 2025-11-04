package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val spinnerMonth: Spinner = findViewById(R.id.spinner_month)
        val spinnerDay: Spinner = findViewById(R.id.spinner_day)
        val spinnerYear: Spinner = findViewById(R.id.spinner_year)
        val buttonNext: Button = findViewById(R.id.next_button)

        // Months
        ArrayAdapter.createFromResource(
            this,
            R.array.month_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerMonth.adapter = adapter
        }

        // Days
        ArrayAdapter.createFromResource(
            this,
            R.array.day_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerDay.adapter = adapter
        }

        // Years
        ArrayAdapter.createFromResource(
            this,
            R.array.year_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerYear.adapter = adapter
        }

        buttonNext.setOnClickListener {
            val intent = Intent(this@SignUp, SetupPassword::class.java)
            startActivity(intent)
        }
    }
}
