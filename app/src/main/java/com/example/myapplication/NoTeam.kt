package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Toast
import com.vishnusivadas.advanced_httpurlconnection.PutData
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class NoTeam : AppCompatActivity() {
    private var teamsIdList = mutableListOf<Int>()
    private val teamsNameList = mutableListOf<String>()
    private val teamsInfoList = mutableListOf<String>()
    private val teamsGameList = mutableListOf<String>()
    private val teamsRegion = mutableListOf<String>()
    private val teamsPlayersCount = mutableListOf<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_team)
        val createTeamButton = findViewById<Button>(R.id.createTeam)
        val findTeamButton = findViewById<Button>(R.id.findTeam)
        createTeamButton.setOnClickListener{
            val intent = Intent(this, CreateTeam::class.java)
            startActivity(intent)
            finish()
        }

        findTeamButton.setOnClickListener {
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                val field = arrayOfNulls<String>(1)
                field[0] = "id"
                //Creating array for data
                val data = arrayOfNulls<String>(1)
                data[0] = ""
                val putData = PutData(
                    "http://www.srv184174.hoster-test.ru/cybernament/scripts/allteams.php",
                    "POST",
                    field,
                    data
                )
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        val result: String = putData.result
                        if (result != "Error" && result != "Error: Database connection") {
                            if (result == "[]"){
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }else{
                                updateAllTeamsData(result)
                            }
                        } else {
                            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                //End Write and Read data with URL
            }

            val intent = Intent(this, FindTeam::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun updateAllTeamsData(strJson: String){
        try {
            val parent = JSONArray(strJson)
            val childList = mutableListOf<JSONObject>()
            val sharedPreferences = this.getSharedPreferences("allTeamsInfo",
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
                teamsIdList.add(childList[i].getInt("id"))
                teamsNameList.add(childList[i].getString("name").toString())
                teamsGameList.add(childList[i].getString("game").toString())
                teamsRegion.add(childList[i].getString("region").toString())
                teamsInfoList.add(childList[i].getString("info").toString())
                teamsPlayersCount.add(childList[i].getInt("players_count"))
            }

            for (i in 0 until teamsIdList.count()){
                val putString = "id$i"
                editor.putInt(putString, teamsIdList[i])
            }

            for (i in 0 until teamsNameList.count()){
                val putString = "name$i"
                editor.putString(putString, teamsNameList[i])
            }

            for (i in 0 until teamsGameList.count()){
                val putString = "game$i"
                editor.putString(putString, teamsGameList[i])
            }

            for (i in 0 until teamsRegion.count()){
                val putString = "gameMode$i"
                editor.putString(putString, teamsRegion[i])
            }

            for (i in 0 until teamsInfoList.count()){
                val putString = "info$i"
                editor.putString(putString, teamsInfoList[i])
            }

            for (i in 0 until teamsPlayersCount.count()){
                val putString = "teamsCount$i"
                editor.putInt(putString, teamsPlayersCount[i])
            }
            editor.putInt("teamCount", teamsNameList.count())
            editor.apply()
        } catch (e: JSONException) {
            throw e
        }
    }
    /*private fun updateAllTeamsData(strJson: String) {
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
                tournamentNameList.add(childList[i].getString("name").toString())
                tournamentGameList.add(childList[i].getString("game").toString())
                tournamentGameModeList.add(childList[i].getString("game_mode").toString())
                tournamentFormatList.add(childList[i].getString("format").toString())
                tournamentInfoList.add(childList[i].getString("info").toString())
                tournamentTeamsCountList.add(childList[i].getInt("teams_count"))
                tournamentStartDateList.add(childList[i].getString("start_date"))
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
    }*/
}