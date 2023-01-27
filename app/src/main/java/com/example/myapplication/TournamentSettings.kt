package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vishnusivadas.advanced_httpurlconnection.PutData
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class TournamentSettings : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    private var tournamentIdList = mutableListOf<Int>()
    private val tournamentNameList = mutableListOf<String>()
    private val tournamentInfoList = mutableListOf<String>()
    private val tournamentGameList = mutableListOf<String>()
    private val tournamentGameModeList = mutableListOf<String>()
    private val tournamentStartDateList = mutableListOf<String>()
    private val tournamentFormatList = mutableListOf<String>()
    private val tournamentTeamsCountList = mutableListOf<Int>()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tournament_settings)
        val tournamentName = findViewById<TextView>(R.id.name)
        val tournamentInfo = findViewById<TextView>(R.id.info)
        val tournamentGame = findViewById<TextView>(R.id.game)
        val tournamentGameMode = findViewById<TextView>(R.id.gameMode)
        val tournamentFormat = findViewById<TextView>(R.id.format)
        val tournamentStartDate = findViewById<TextView>(R.id.startDate)
        val tournamentTeamsCount = findViewById<TextView>(R.id.teamsCount)
        val tournamentRequest = findViewById<Button>(R.id.requestButton)

        val loginPrefs = this.getSharedPreferences("loginInfo",
            MODE_PRIVATE
        )
        val id = loginPrefs.getInt("userId", 0)

        val myTournaments = findViewById<Button>(R.id.myTournaments)
        val allTournament = findViewById<Button>(R.id.allTournaments)
        val myTeam = findViewById<Button>(R.id.myTeam)
        val profile = findViewById<Button>(R.id.profile)

        myTournaments.setOnClickListener{
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

        allTournament.setOnClickListener{
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

        myTeam.setOnClickListener {
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

        val sharedPreferences: SharedPreferences = this.getSharedPreferences("tournamentInfoToSettings", MODE_PRIVATE)
        val tournamentId = sharedPreferences.getString("id", "default")
        tournamentName.text = sharedPreferences.getString("name","default")
        tournamentInfo.text = sharedPreferences.getString("info","default")
        tournamentGame.text = sharedPreferences.getString("game","default")
        tournamentGameMode.text = sharedPreferences.getString("gameMode","default")
        tournamentFormat.text = sharedPreferences.getString("format","default")
        tournamentStartDate.text = sharedPreferences.getString("startDate","default")
        tournamentTeamsCount.text = sharedPreferences.getString("teamsCount","default")
        tournamentRequest.setOnClickListener {
            val intent = Intent(this, Requests::class.java)
            intent.putExtra("id", tournamentId)
            startActivity(intent)
            finish()
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