package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vishnusivadas.advanced_httpurlconnection.PutData
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private var tournamentIdList = mutableListOf<Int>()
    private val tournamentNameList = mutableListOf<String>()
    private val tournamentInfoList = mutableListOf<String>()
    private val tournamentGameList = mutableListOf<String>()
    private val tournamentGameModeList = mutableListOf<String>()
    private val tournamentStartDateList = mutableListOf<String>()
    private val tournamentFormatList = mutableListOf<String>()
    private val tournamentTeamsCountList = mutableListOf<Int>()

    private val teamNameList = mutableListOf<String>()
    private val teamInfoList = mutableListOf<String>()
    private val teamGameList = mutableListOf<String>()
    private val teamRegionList = mutableListOf<String>()
    private val teamPlayersCountList = mutableListOf<Int>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setContentView(R.layout.all_tournament_item_list)
        val tournamentSettings = findViewById<Button>(R.id.tournamentRegistration)
        setContentView(R.layout.activity_main)
        val myTournamentsButton = findViewById<Button>(R.id.myTournaments)
        val allTournamentsButton = findViewById<Button>(R.id.allTournaments)
        val myTeamButton = findViewById<Button>(R.id.myTeam)
        val profile = findViewById<Button>(R.id.profile)
        val sharedPreferences = this.getSharedPreferences("loginInfo",
            MODE_PRIVATE
        )
        val id = sharedPreferences.getInt("userId", 0)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val tournamentPref = this.getSharedPreferences("allTournamentInfo",
            Context.MODE_PRIVATE)
        val getId = "id"
        val getName = "name"
        val getInfo = "info"
        val getStartDate = "startDate"
        val getGame = "game"
        val getGameMode = "gameMode"
        val getFormat = "format"
        val getTeamsCount = "teamsCount"
        val tournamentCount = tournamentPref.getInt("tournamentCount",0)
        val dataArray = arrayOfNulls<AllTournamentInfo>(tournamentCount)
        for (i in 0 until tournamentCount+1){
            if (tournamentPref.getString("$getName$i","")!=""){
                val tournamentId = tournamentPref.getInt("$getId$i", 0)
                val tournamentName: String = tournamentPref.getString("$getName$i","").toString()
                val tournamentInfo = tournamentPref.getString("$getInfo$i", "").toString()
                val tournamentStartDate = tournamentPref.getString("$getStartDate$i", "").toString()
                val tournamentGame = tournamentPref.getString("$getGame$i", "").toString()
                val tournamentGameMode = tournamentPref.getString("$getGameMode$i", "").toString()
                val tournamentFormat = tournamentPref.getString("$getFormat$i", "").toString()
                val tournamentTeamsCount = tournamentPref.getInt("$getTeamsCount$i", 0)
                dataArray[i] = AllTournamentInfo(tournamentId,"$tournamentName", "$tournamentInfo", "$tournamentGame",
                    "$tournamentGameMode", "$tournamentStartDate", "$tournamentFormat",
                    tournamentTeamsCount, tournamentSettings
                )

                val allTournamentInfoAdapter =
                    AllTournamentInfoAdapter(
                        dataArray,
                        MainActivity()
                    )
                recyclerView.adapter = allTournamentInfoAdapter
            }
        }

        val teamInfoPref = this.getSharedPreferences("teamInfo",
            Context.MODE_PRIVATE)
        val teamId = teamInfoPref.getInt("id",0)

        allTournamentsButton.setOnClickListener{
            if(id!=0) {
                val handler = Handler(Looper.getMainLooper())
                handler.post {
                    val field = arrayOfNulls<String>(1)
                    field[0] = "id"
                    //Creating array for data
                    val data = arrayOfNulls<String>(1)
                    data[0] = id.toString()
                    val putData = PutData(
                        "http://www.srv184174.hoster-test.ru/cybernament/scripts/alltournaments.php",
                        "POST",
                        field,
                        data
                    )
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            val result: String = putData.result
                            if (result != "Error" && result != "Error: Database connection") {
                                if (result == "[]"){
                                    val intent = Intent(this, NoTournaments::class.java)
                                    startActivity(intent)
                                    finish()
                                }else{
                                    updateAllTournamentsData(result)
                                    val intent = Intent(this, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            } else {
                                Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    //End Write and Read data with URL
                }
            }
        }

        myTournamentsButton.setOnClickListener{
            if(id!=0) {
                val handler = Handler(Looper.getMainLooper())
                handler.post {
                    val field = arrayOfNulls<String>(1)
                    field[0] = "id"
                    //Creating array for data
                    val data = arrayOfNulls<String>(1)
                    data[0] = id.toString()
                    val putData = PutData(
                        "http://www.srv184174.hoster-test.ru/cybernament/scripts/mytournaments.php",
                        "POST",
                        field,
                        data
                    )
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            val result = putData.result
                            if (result != "Error" && result != "Error: Database connection") {
                                if (result == "[]"){
                                    val intent = Intent(this, NoTournaments::class.java)
                                    startActivity(intent)
                                    finish()
                                }else{
                                    updateMyTournamentsData(result)
                                    val intent = Intent(this, MyTournaments::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            } else {
                                Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    //End Write and Read data with URL
                }
            }
            else{
                Toast.makeText(this,"you are not logged!", Toast.LENGTH_SHORT).show()
            }
        }

        myTeamButton.setOnClickListener {
            if (id != 0) {
                val handler = Handler(Looper.getMainLooper())
                handler.post {
                    val field = arrayOfNulls<String>(1)
                    field[0] = "id"
                    //Creating array for data
                    val data = arrayOfNulls<String>(1)
                    data[0] = id.toString()
                    val putData = PutData(
                        "http://www.srv184174.hoster-test.ru/cybernament/scripts/myteam.php",
                        "POST",
                        field,
                        data
                    )
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            val result = putData.result
                            if (result != "Error" && result != "Error: Database connection") {
                                if (result == "No Team"){
                                    val intent = Intent(this, NoTeam::class.java)
                                    startActivity(intent)
                                    finish()
                                }else{
                                    updateMyTeamData(result)
                                    val intent = Intent(this, MyTeam::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            } else {
                                val intent = Intent(this, NoTeam::class.java)
                                startActivity(intent)
                                finish()
                                Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    //End Write and Read data with URL
                }
            } else {
                Toast.makeText(this, "you are not logged!", Toast.LENGTH_SHORT).show()
            }
        }

        profile.setOnClickListener {
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun updateAllTournamentsData(strJson: String) {
        try {
            val parent = JSONArray(strJson)
            val childList = mutableListOf<JSONObject>()
            val sharedPreferences = this.getSharedPreferences("allTournamentInfo",
                Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            if (parent.length()>1){
                for (i in 0 until parent.length()){
                    val child = parent.getJSONObject(i)
                    childList.add(child)
                }
            }else{
                val child = parent.getJSONObject(0)
                childList.add(child)
            }

            for (i in 0 until childList.count()){
                tournamentIdList.add(childList[i].getInt("id"))
                tournamentNameList.add(childList[i].getString("name").toString())
                tournamentGameList.add(childList[i].getString("game").toString())
                tournamentGameModeList.add(childList[i].getString("game_mode").toString())
                tournamentFormatList.add(childList[i].getString("format").toString())
                tournamentInfoList.add(childList[i].getString("info").toString())
                tournamentTeamsCountList.add(childList[i].getInt("teams_count"))
                tournamentStartDateList.add(childList[i].getString("start_date"))
            }

            for (i in 0 until tournamentIdList.count()){
                val putString = "id$i"
                editor.putInt(putString, tournamentIdList[i])
            }

            for (i in 0 until tournamentNameList.count()){
                val putString = "name$i"
                editor.putString(putString, tournamentNameList[i])
            }

            for (i in 0 until tournamentGameList.count()){
                val putString = "game$i"
                editor.putString(putString, tournamentGameList[i])
            }

            for (i in 0 until tournamentGameModeList.count()){
                val putString = "gameMode$i"
                editor.putString(putString, tournamentGameModeList[i])
            }

            for (i in 0 until tournamentFormatList.count()){
                val putString = "format$i"
                editor.putString(putString, tournamentFormatList[i])
            }

            for (i in 0 until tournamentInfoList.count()){
                val putString = "info$i"
                editor.putString(putString, tournamentInfoList[i])
            }

            for (i in 0 until tournamentTeamsCountList.count()){
                val putString = "teamsCount$i"
                editor.putInt(putString, tournamentTeamsCountList[i])
            }

            for (i in 0 until tournamentStartDateList.count()){
                val putString = "startDate$i"
                editor.putString(putString, tournamentStartDateList[i])
            }
            editor.putInt("tournamentCount", tournamentNameList.count())
            editor.apply()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } catch (e: JSONException) {
            throw e
        }
    }

    private fun updateMyTournamentsData(strJson: String) {
        try {
            val parent = JSONArray(strJson)
            val childList = mutableListOf<JSONObject>()
            val sharedPreferences = this.getSharedPreferences("tournamentInfo",
                Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            if (parent.length()>1){
                for (i in 0 until parent.length()){
                    val child = parent.getJSONObject(i)
                    childList.add(child)
                }
            }else{
                val child = parent.getJSONObject(0)
                childList.add(child)
            }

            for (i in 0 until childList.count()){
                tournamentIdList.add(childList[i].getInt("id"))
                tournamentNameList.add(childList[i].getString("name").toString())
                tournamentGameList.add(childList[i].getString("game").toString())
                tournamentGameModeList.add(childList[i].getString("game_mode").toString())
                tournamentFormatList.add(childList[i].getString("format").toString())
                tournamentInfoList.add(childList[i].getString("info").toString())
                tournamentTeamsCountList.add(childList[i].getInt("teams_count"))
                tournamentStartDateList.add(childList[i].getString("start_date"))
            }
            for (i in 0 until tournamentIdList.count()){
                val putString = "id$i"
                editor.putInt(putString, tournamentIdList[i])
            }

            for (i in 0 until tournamentNameList.count()){
                val putString = "name$i"
                editor.putString(putString, tournamentNameList[i])
            }

            for (i in 0 until tournamentGameList.count()){
                val putString = "game$i"
                editor.putString(putString, tournamentGameList[i])
            }

            for (i in 0 until tournamentGameModeList.count()){
                val putString = "gameMode$i"
                editor.putString(putString, tournamentGameModeList[i])
            }

            for (i in 0 until tournamentFormatList.count()){
                val putString = "format$i"
                editor.putString(putString, tournamentFormatList[i])
            }

            for (i in 0 until tournamentInfoList.count()){
                val putString = "info$i"
                editor.putString(putString, tournamentInfoList[i])
            }

            for (i in 0 until tournamentTeamsCountList.count()){
                val putString = "teamsCount$i"
                editor.putInt(putString, tournamentTeamsCountList[i])
            }

            for (i in 0 until tournamentStartDateList.count()){
                val putString = "startDate$i"
                editor.putString(putString, tournamentStartDateList[i])
            }
            editor.putInt("tournamentCount", tournamentNameList.count())
            editor.apply()
            val intent = Intent(this, MyTournaments::class.java)
            startActivity(intent)
            finish()
        } catch (e: JSONException) {
            throw e
        }
    }

    private fun updateMyTeamData(strJson: String) {
        try {
            val parent = JSONArray(strJson)
            val child = parent.getJSONObject(0)
            val pref = this.getSharedPreferences("teamInfo",
                Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = pref.edit()
            val teamId = child.getInt("id")
            val teamName = child.getString("name").toString()
            val teamGame = child.getString("game").toString()
            val teamInfo = child.getString("info").toString()
            val teamRegion = child.getString("region").toString()
            val teamPlayersCount = child.getInt("players_count")

            editor.putInt("id", teamId)
            editor.putString("name", teamName)
            editor.putString("game", teamGame)
            editor.putString("info", teamInfo)
            editor.putString("region", teamRegion)
            editor.putInt("playersCount", teamPlayersCount)
            editor.apply()

            val intent = Intent(this, MyTournaments::class.java)
            startActivity(intent)
            finish()
        } catch (e: JSONException) {
            throw e
        }
    }
}