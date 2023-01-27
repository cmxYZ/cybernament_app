package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vishnusivadas.advanced_httpurlconnection.PutData
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class FindTeam : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.team_item_list)
        val requestToTeam = findViewById<Button>(R.id.requestToTeam)
        setContentView(R.layout.activity_find_team)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, NoTeam::class.java)
            startActivity(intent)
            finish()
        }
        val teamsPref = this.getSharedPreferences("allTeamsInfo",
            Context.MODE_PRIVATE)
        val getId = "id"
        val getName = "name"
        val getGame = "game"
        val getInfo = "info"
        val getRegion= "region"
        val getPlayersCount = "playersCount"
        val teamCount = teamsPref.getInt("teamCount",0)
        val dataArray = arrayOfNulls<AllTeamsInfo>(teamCount)
        for (i in 0 until teamCount+1){
            if (teamsPref.getString("$getName$i","")!=""){
                val teamId = teamsPref.getInt("$getId$i", 0)
                val teamName = teamsPref.getString("$getName$i","").toString()
                val teamGame = teamsPref.getString("$getGame$i", "").toString()
                val teamInfo = teamsPref.getString("$getInfo$i", "нет описания").toString()
                val teamRegion = teamsPref.getString("$getRegion$i", "world").toString()
                val teamPlayersCount = teamsPref.getInt("$getPlayersCount$i", 0)
                val count = teamPlayersCount.toString()
                dataArray[i] = AllTeamsInfo(teamId,"$teamName", "$teamGame", "$teamInfo",
                    "$teamRegion", teamPlayersCount, requestToTeam)

                val allTeamsInfoAdapter =
                    AllTeamsInfoAdapter(
                        dataArray,
                        FindTeam()
                    )
                recyclerView.adapter = allTeamsInfoAdapter
            }
        }
    }
}