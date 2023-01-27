package com.example.myapplication

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
import com.google.android.material.textfield.TextInputLayout
import com.vishnusivadas.advanced_httpurlconnection.PutData
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

@Suppress("NAME_SHADOWING")
class Login : AppCompatActivity() {
    private var loggedUsername: String = ""
    private var loggedId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val textInputLayoutUsername = findViewById<TextInputLayout>(R.id.textInputLayoutUsername)
        val textInputLayoutPassword = findViewById<TextInputLayout>(R.id.textInputLayoutPassword)
        val signUpButton: Button = findViewById(R.id.loginButton)
        val signUpText = findViewById<TextView>(R.id.signUpText)

        signUpText.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }


        signUpButton.setOnClickListener{
            val username: String = textInputLayoutUsername.editText?.text.toString()
            val password: String = textInputLayoutPassword.editText?.text.toString()

            if(username != "" && password != "") {
                val handler = Handler(Looper.getMainLooper())
                handler.post {
                    val field = arrayOfNulls<String>(2)
                    field[0] = "username"
                    field[1] = "password"
                    //Creating array for data
                    val data = arrayOfNulls<String>(2)
                    data[0] = username
                    data[1] = password
                    val putData = PutData(
                        "http://www.srv184174.hoster-test.ru/cybernament/scripts/login.php",
                        "POST",
                        field,
                        data
                    )
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            val result = putData.result
                            if (result != "Error" && result != "Wrong Password or Username") {
                                updateUserData(result)
                                val username: String = loggedUsername
                                val userId: Int = loggedId
                                val sharedPreferences = this.getSharedPreferences("loginInfo",
                                    Context.MODE_PRIVATE)
                                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                                editor.putString("username", username)
                                editor.putInt("userId", userId)
                                editor.apply()
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

    private fun updateUserData(strJson: String?) {
        try {
            val parent = JSONArray(strJson)
            val child: JSONObject = parent.getJSONObject(0)
            loggedId = child.getInt("id")
            loggedUsername = child.getString("username")
        }catch (e: JSONException) {
            throw e
        }
    }

}