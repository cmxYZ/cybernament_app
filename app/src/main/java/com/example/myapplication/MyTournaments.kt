package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MyTournaments : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tournament_item_list)
        val tournamentSettings = findViewById<Button>(R.id.tournamentRegistration)
        setContentView(R.layout.activity_my_tournaments)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val sharedPreferences = this.getSharedPreferences("tournamentInfo",
            Context.MODE_PRIVATE)
        val getId = "id"
        val getName = "name"
        val getInfo = "info"
        val getStartDate = "startDate"
        val getGame = "game"
        val getGameMode = "gameMode"
        val getFormat = "format"
        val getTeamsCount = "teamsCount"
        val tournamentCount = sharedPreferences.getInt("tournamentCount",0)
        val dataArray = arrayOfNulls<TournamentInfo>(tournamentCount)
        for (i in 0 until tournamentCount){
            if (sharedPreferences.getString("$getName$i","")!=""){
                val tournamentId = sharedPreferences.getInt("$getId$i",0)
                val tournamentName: String = sharedPreferences.getString("$getName$i","").toString()
                val tournamentInfo = sharedPreferences.getString("$getInfo$i", "").toString()
                val tournamentStartDate = sharedPreferences.getString("$getStartDate$i", "").toString()
                val tournamentGame = sharedPreferences.getString("$getGame$i", "").toString()
                val tournamentGameMode = sharedPreferences.getString("$getGameMode$i", "").toString()
                val tournamentFormat = sharedPreferences.getString("$getFormat$i", "").toString()
                val tournamentTeamsCount = sharedPreferences.getInt("$getTeamsCount$i", 0)
                dataArray[i] = TournamentInfo(tournamentId,"$tournamentName", "$tournamentInfo", "$tournamentGame",
                    "$tournamentGameMode", "$tournamentStartDate", "$tournamentFormat",
                    tournamentTeamsCount, tournamentSettings
                )

                val tournamentInfoAdapter =
                    TournamentInfoAdapter(
                        dataArray,
                        MyTournaments()
                    )
                recyclerView.adapter = tournamentInfoAdapter
            }
        }
    }
}
