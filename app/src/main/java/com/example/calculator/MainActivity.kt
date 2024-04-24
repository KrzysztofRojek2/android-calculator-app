package com.example.calculator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val simpleCalculatorLayoutButton = findViewById<Button>(R.id.btn_simpleCalculator)
        simpleCalculatorLayoutButton.setOnClickListener {
            val intent = Intent(this, SimpleCalculatorActivity::class.java)
            startActivity(intent)
        }

        val advancedCalculatorLayoutButton = findViewById<Button>(R.id.btn_advancedCalculator)
        advancedCalculatorLayoutButton.setOnClickListener {
            val intent = Intent(this, AdvancedCalculatorActivity::class.java)
            startActivity(intent)
        }

        val aboutLayoutButton = findViewById<Button>(R.id.btn_about)
        aboutLayoutButton.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        val exitButton = findViewById<Button>(R.id.btn_exit)
        exitButton.setOnClickListener {
            finish()
        }

    }
}