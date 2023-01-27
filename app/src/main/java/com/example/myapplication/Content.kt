package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Content : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)
        val createTournamentButton : Button = findViewById(R.id.createTournamentButton)
        val createLeagueButton : Button = findViewById(R.id.createLeagueButton)

        createTournamentButton.setOnClickListener{
            val tournamentIntent = Intent(this, TournamentGameChoose::class.java)
            startActivity(tournamentIntent)
            finish()
        }

        createLeagueButton.setOnClickListener{
            val leagueIntent = Intent(this, LeagueGameChoose::class.java)
            startActivity(leagueIntent)
            finish()
        }
    }
}