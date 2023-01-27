package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.vishnusivadas.advanced_httpurlconnection.PutData

class TournamentRegistration : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tournament_registration)
        val tournamentRequestConfirm = findViewById<Button>(R.id.tournamentRegistrationConfirm)
        val tournamentPref = this.getSharedPreferences("tournamentInfoToSettings",
            Context.MODE_PRIVATE)
        val tournamentId = tournamentPref.getInt("id", 0)
        val sharedPreferences = this.getSharedPreferences("teamInfo",
            Context.MODE_PRIVATE)
        val teamId = sharedPreferences.getInt("id",0)

        val tournamentName = findViewById<TextView>(R.id.tournamentName)
        val tournamentGame = findViewById<TextView>(R.id.tournamentGame)
        val tournamentGameMode = findViewById<TextView>(R.id.tournamentGameMode)
        val tournamentInfo = findViewById<TextView>(R.id.tournamentInfo)
        val tournamentFormat = findViewById<TextView>(R.id.tournamentFormat)
        val tournamentStartDate = findViewById<TextView>(R.id.tournamentStartDate)
        val tournamentTeamsCount = findViewById<TextView>(R.id.tournamentTeamsCount)

        tournamentName.text = tournamentPref.getString("name", "default")
        tournamentGame.text = tournamentPref.getString("game", "default")
        tournamentGameMode.text = tournamentPref.getString("gameMode", "default")
        tournamentInfo.text = tournamentPref.getString("info", "default")
        tournamentFormat.text = tournamentPref.getString("format", "default")
        tournamentStartDate.text = tournamentPref.getString("startDate", "default")
        tournamentTeamsCount.text = tournamentPref.getString("teamsCount", "default")
        tournamentRequestConfirm.setOnClickListener {
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                val field = arrayOfNulls<String>(2)
                field[0] = "tournament_id"
                field[1] = "team_id"
                //Creating array for data
                val data = arrayOfNulls<String>(2)
                data[0] = tournamentId.toString()
                data[1] = teamId.toString()
                val putData = PutData(
                    "http://www.srv184174.hoster-test.ru/cybernament/scripts/tournamentrequest.php",
                    "POST",
                    field,
                    data
                )
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        val result: String = putData.result
                        if (result != "Error" && result != "Error: Database connection") {
                            UpdateRegistrationRequestData()
                            val intent = Intent(this, MainActivity::class.java)
                            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                //End Write and Read data with URL
            }
        }
    }
    fun UpdateRegistrationRequestData(){
        val sharedPreferences = this.getSharedPreferences("requestData", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val teamInfoPref = this.getSharedPreferences("teamInfo",
            Context.MODE_PRIVATE)
        val teamId = teamInfoPref.getInt("id",0)
        val teamName = teamInfoPref.getString("name", "default")
        editor.putInt("teamId", teamId)
        editor.putString("name", teamName)
        editor.apply()
    }
}