package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Requests : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_requests)

        setContentView(R.layout.activity_request_item)
        val requestApply = findViewById<Button>(R.id.applyToTournament)
        setContentView(R.layout.activity_requests)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewRequest)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val tournamentInfo = this.getSharedPreferences("allTournamentInfo",
            Context.MODE_PRIVATE)
        val teamInfo = this.getSharedPreferences("teamInfo",
            Context.MODE_PRIVATE)
        val getId = "id"
        val getName = "name"
        val requestCount = tournamentInfo.getInt("tournamentCount",0)
        val dataArray = arrayOfNulls<RequestInfo>(requestCount)
        for (i in 0 until requestCount){
            if (tournamentInfo.getString("$getName$i","")!=""){
                val tournamentId = tournamentInfo.getInt("$getId$i",0)
                val teamName: String = teamInfo.getString("name","default").toString()
                dataArray[i] = RequestInfo(tournamentId,"$teamName", requestApply)

                val requestInfoAdapter =
                    RequestInfoAdapter(
                        dataArray,
                        Requests()
                    )
                recyclerView.adapter = requestInfoAdapter
            }
        }
    }
}