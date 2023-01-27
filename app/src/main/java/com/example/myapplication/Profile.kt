package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Profile : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val signOutButton = findViewById<Button>(R.id.signOut)

        signOutButton.setOnClickListener {
            val sharedPreferences = this.getSharedPreferences("loginInfo",
                Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putInt("userId", 0)
            editor.putString("username", "")
            editor.apply()
            val  intent = Intent(this, CheckLogin::class.java)
            startActivity(intent)
            finish()
        }
    }
}