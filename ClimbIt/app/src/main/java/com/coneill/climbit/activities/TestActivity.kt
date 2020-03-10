package com.coneill.climbit.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.climbit.R

class TestActivity : AppCompatActivity() {

    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        initViews()

    }

    /**
     * Initialise all buttons, fragments, textViews etc.
     */
    private fun initViews() {
        button = findViewById(R.id.button)

    }
}
