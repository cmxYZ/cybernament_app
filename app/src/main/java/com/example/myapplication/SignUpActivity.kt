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


class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        //Start ProgressBar first (Set visibility VISIBLE)

        val textInputLayoutUsername = findViewById<TextInputLayout>(R.id.textInputLayoutUsername)
        val textInputLayoutEmail = findViewById<TextInputLayout>(R.id.textInputLayoutEmail)
        val textInputLayoutPassword = findViewById<TextInputLayout>(R.id.textInputLayoutPassword)
        val signUpButton: Button = findViewById(R.id.signUpButton)
        val loginText = findViewById<TextView>(R.id.loginText)
        val region = "world"

        loginText.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

        signUpButton.setOnClickListener{
            val username: String = textInputLayoutUsername.editText?.text.toString()
            val email: String = textInputLayoutEmail.editText?.text.toString()
            val password: String = textInputLayoutPassword.editText?.text.toString()

            if(username != "" && email != "" && password != "" && region != "") {
                val handler = Handler(Looper.getMainLooper())
                handler.post {
                    val field = arrayOfNulls<String>(4)
                    field[0] = "username"
                    field[1] = "email"
                    field[2] = "password"
                    field[3] = "region"

                    //Creating array for data
                    val data = arrayOfNulls<String>(4)
                    data[0] = username
                    data[1] = email
                    data[2] = password
                    data[3] = region
                    val putData = PutData(
                        "http://www.srv184174.hoster-test.ru/cybernament/scripts/signup.php",
                        "POST",
                        field,
                        data
                    )
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            val result = putData.result
                            if (result.equals("Success")) {
                                val intent = Intent(this, Login::class.java)
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