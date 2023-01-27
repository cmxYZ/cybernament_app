package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivityOrganizer : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_oragnizer)
        val organization: Button = findViewById(R.id.organizationSettings)
        organization.setOnClickListener{
            var intent = Intent(this, NoTournaments::class.java)
            startActivity(intent)
            finish()
        }
    }

}