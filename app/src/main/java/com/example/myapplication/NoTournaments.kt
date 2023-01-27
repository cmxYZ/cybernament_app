package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class NoTournaments : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_mytournaments)
        val createTournament : Button = findViewById(R.id.createTournamentButton)
        createTournament.setOnClickListener{
            var intent = Intent(this, TournamentGameChoose::class.java)
            startActivity(intent)
            finish()
        }
    }
}