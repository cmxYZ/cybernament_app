package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MyTeam : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_team)
        val sharedPreferences = this.getSharedPreferences("teamInfo",
            Context.MODE_PRIVATE)
        val teamNameView = findViewById<TextView>(R.id.teamName)
        val teamGameView = findViewById<TextView>(R.id.teamGame)
        val teamInfoView = findViewById<TextView>(R.id.teamInfo)
        val teamRegionView = findViewById<TextView>(R.id.teamRegion)
        val teamPlayersCountView = findViewById<TextView>(R.id.teamPlayersCount)

        val goBack = findViewById<Button>(R.id.goBackButton)

        goBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val teamId = sharedPreferences.getInt("id", 0)
        val teamName: String = sharedPreferences.getString("name","").toString()
        val teamGame = sharedPreferences.getString("game", "").toString()
        val teamInfo = sharedPreferences.getString("info", "").toString()
        val teamRegion = sharedPreferences.getString("region", "").toString()
        val teamPlayersCount = sharedPreferences.getInt("playersCount", 0)
        val count = teamPlayersCount.toString()


        teamNameView.text = "Название команды: $teamName"
        teamGameView.text = "Игра: $teamGame"
        teamInfoView.text = "Описание: $teamInfo"
        teamRegionView.text = "Регион: $teamRegion"
        teamPlayersCountView.text = "Количество игроков в команде: $count"
        val editor = sharedPreferences.edit()
        editor.putInt("id", teamId)
    }
}