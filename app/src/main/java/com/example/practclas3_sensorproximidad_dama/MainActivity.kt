package com.example.practclas3_sensorproximidad_dama

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnProximity = findViewById<Button>(R.id.btnProximity)
        val btnLuminosity = findViewById<Button>(R.id.btnLuminosity)

        btnProximity.setOnClickListener {
            val intent = Intent(this, ProximityActivity::class.java)
            startActivity(intent)
        }

        btnLuminosity.setOnClickListener {
            val intent = Intent(this, LuminosityActivity::class.java)
            startActivity(intent)
        }
    }
}