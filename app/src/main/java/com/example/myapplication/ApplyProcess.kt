package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.vishnusivadas.advanced_httpurlconnection.PutData

class ApplyProcess : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_process)
        val pref = getSharedPreferences("requestData", MODE_PRIVATE)
        val tournamentId = pref.getString("tournament_id", "0")
        val teamId = pref.getInt("team_id", 0)
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            val field = arrayOfNulls<String>(2)
            field[0] = "team_id"
            field[1] = "tournament_id"
            //Creating array for data
            val data = arrayOfNulls<String>(2)
            data[0] = teamId.toString()
            data[1] = tournamentId
            val putData = PutData(
                "http://www.srv184174.hoster-test.ru/cybernament/scripts/acceptrequest.php ",
                "POST",
                field,
                data
            )
            if (putData.startPut()) {
                if (putData.onComplete()) {
                    val result = putData.result
                    if (result != "Error" && result != "Error: Database connection") {
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
}