package com.example.a7minworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ll_start.setOnClickListener {
            val intent = Intent(this,ExerciseActivity::class.java)
            startActivity(intent)
        }

        ll_bmi_calculator.setOnClickListener {
            val intent1 = Intent(this,BMIActivity::class.java)
            startActivity(intent1)
        }

        ll_history.setOnClickListener {
            val intent2 = Intent(this,HistoryActivity::class.java)
            startActivity(intent2)
        }
    }
}