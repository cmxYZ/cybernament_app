package com.example.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class RequestSends : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_sends)
        val teamsPref = this.getSharedPreferences("allTeamsInfo",
            Context.MODE_PRIVATE)

    }
}