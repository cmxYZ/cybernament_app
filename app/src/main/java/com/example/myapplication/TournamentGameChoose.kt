package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

open class TournamentGameChoose : AppCompatActivity() {
    var gameChosen : String = ""
    @SuppressLint("WrongViewCast", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tournament_game_choose)
        val pubgChoose = findViewById<ImageButton>(R.id.pubgChoose)
        val valorantChoose = findViewById<ImageButton>(R.id.valorantChoose)
        val brawlStarsChoose = findViewById<ImageButton>(R.id.brawlStarsChoose)
        val freeFireChoose = findViewById<ImageButton>(R.id.freeFireChoose)
        val standoff2Choose = findViewById<ImageButton>(R.id.standoff2Choose)
        val clashOfClansChoose = findViewById<ImageButton>(R.id.clashOfClansChoose)
        val apexLegendsChoose = findViewById<ImageButton>(R.id.apexLegendsChoose)
        val clashRoyalChoose = findViewById<ImageButton>(R.id.clashRoyalChoose)
        val mobileLegendsChoose = findViewById<ImageButton>(R.id.mobileLegendsChoose)

        pubgChoose.setOnClickListener{
            this.gameChosen = "Pubg"
            val intent = Intent(this, TournamentCustomization::class.java)
            intent.putExtra("game", gameChosen)
            startActivity(intent)
            finish()
        }

        valorantChoose.setOnClickListener{
            this.gameChosen = "Valorant"
            val intent = Intent(this, TournamentCustomization::class.java)
            intent.putExtra("game", gameChosen)
            startActivity(intent)
            finish()
        }

        brawlStarsChoose.setOnClickListener{
            this.gameChosen = "Brawl Stars"
            val intent = Intent(this, TournamentCustomization::class.java)
            intent.putExtra("game", gameChosen)
            startActivity(intent)
            finish()
        }

        freeFireChoose.setOnClickListener{
            this.gameChosen = "Free Fire"
            val intent = Intent(this, TournamentCustomization::class.java)
            intent.putExtra("game", gameChosen)
            startActivity(intent)
            finish()
        }

        standoff2Choose.setOnClickListener{
            this.gameChosen = "Standoff 2"
            val intent = Intent(this, TournamentCustomization::class.java)
            intent.putExtra("game", gameChosen)
            startActivity(intent)
            finish()
        }

        clashOfClansChoose.setOnClickListener{
            this.gameChosen = "Clash Of Clans"
            val intent = Intent(this, TournamentCustomization::class.java)
            intent.putExtra("game", gameChosen)
            startActivity(intent)
            finish()
        }

        apexLegendsChoose.setOnClickListener{
            this.gameChosen = "Apex Legends"
            val intent = Intent(this, TournamentCustomization::class.java)
            intent.putExtra("game", gameChosen)
            startActivity(intent)
            finish()
        }

        clashRoyalChoose.setOnClickListener{
            this.gameChosen = "Clash Royal"
            val intent = Intent(this, TournamentCustomization::class.java)
            intent.putExtra("game", gameChosen)
            startActivity(intent)
            finish()
        }

        mobileLegendsChoose.setOnClickListener{
            this.gameChosen = "Mobile Legends"
            val intent = Intent(this, TournamentCustomization::class.java)
            intent.putExtra("game", gameChosen)
            startActivity(intent)
            finish()
        }
    }
}