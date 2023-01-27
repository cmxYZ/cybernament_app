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

class ChooseRoleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_role)
        val selectOrganizerButton = findViewById<Button>(R.id.selectOrganizerButton)
        val result = getSharedPreferences("username", Context.MODE_PRIVATE)
        val username = result.getString("username", "error")
        getUserId(username)
        selectOrganizerButton.setOnClickListener{
            val intent = Intent(this, MainActivityOrganizer::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getUserId(username:String?){
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            val field = arrayOfNulls<String>(2)
            field[0] = "username"
            //Creating array for data
            val data = arrayOfNulls<String>(2)
            data[0] = username
            val putData = PutData(
                "http://192.168.0.103/LoginRegister/getUserId.php",
                "POST",
                field,
                data
            )
            if (putData.startPut()) {
                if (putData.onComplete()) {
                    val result = putData.result
                    if (result.equals("Login Success")) {
                        val sharedPreferences: SharedPreferences =
                            this.getSharedPreferences("loginInfo", Context.MODE_PRIVATE)
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString("username", username)
                        editor.apply()
                        val intent = Intent(this, ChooseRoleActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}