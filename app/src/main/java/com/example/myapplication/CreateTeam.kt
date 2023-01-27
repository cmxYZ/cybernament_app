package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.vishnusivadas.advanced_httpurlconnection.PutData

class CreateTeam : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_team)
        val textInputTeamName = findViewById<TextInputEditText>(R.id.teamName)
        val textInputTeamGame = findViewById<TextInputEditText>(R.id.teamGame)
        val textInputTeamDescription = findViewById<TextInputEditText>(R.id.teamInfo)
        val createTeamButton: Button = findViewById(R.id.createTeamButton)
        val regionSpinner : Spinner = findViewById(R.id.teamRegion)

        ArrayAdapter.createFromResource(this, R.array.regions,android.R.layout.simple_spinner_item).also {
                adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            regionSpinner.adapter = adapter
        }

        createTeamButton.setOnClickListener{
            val teamName: String = textInputTeamName.text.toString()
            val teamGame: String = textInputTeamGame.text.toString()
            val teamDescription: String = textInputTeamDescription.text.toString()
            val teamRegion : String = regionSpinner.selectedItem.toString()
            val sharedPreferences = this.getSharedPreferences("loginInfo",
                Context.MODE_PRIVATE)
            val id = sharedPreferences.getInt("userId", 0)
            val creatorId = id.toString()
            val playersCount = 4

            if(teamName != "" && teamGame != "" && teamRegion != "") {
                val handler = Handler(Looper.getMainLooper())
                handler.post {
                    val field = arrayOfNulls<String>(6)
                    field[0] = "creator_id"
                    field[1] = "name"
                    field[2] = "info"
                    field[3] = "region"
                    field[4] = "game"
                    field[5] = "players_count"
                    //Creating array for data
                    val data = arrayOfNulls<String>(6)
                    data[0] = creatorId
                    data[1] = teamName
                    data[2] = teamDescription
                    data[3] = teamRegion
                    data[4] = teamGame
                    data[5] = playersCount.toString()
                    val putData = PutData(
                        "http://www.srv184174.hoster-test.ru/cybernament/scripts/createteam.php",
                        "POST",
                        field,
                        data
                    )
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            val result = putData.result
                            if (result.equals("Success")) {
                                val intent = Intent(this, MainActivity::class.java)
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
            else{
                Toast.makeText(this,"All fields are required!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}