package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.vishnusivadas.advanced_httpurlconnection.PutData
import android.widget.ArrayAdapter
import android.widget.Spinner

class CreateOrganization : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_organization)

        val textInputOrganizationName = findViewById<TextInputEditText>(R.id.organizationName)
        val textInputOrganizationEmail = findViewById<TextInputEditText>(R.id.organizationEmail)
        val textInputOrganizationDescription = findViewById<TextInputEditText>(R.id.organizationDescription)
        val createOrganizationButton: Button = findViewById(R.id.createOrganizationButton)
        val regionSpinner : Spinner = findViewById(R.id.organizationRegion)

        ArrayAdapter.createFromResource(this, R.array.regions,android.R.layout.simple_spinner_item).also {
            adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            regionSpinner.adapter = adapter
        }

        createOrganizationButton.setOnClickListener{
            val organizationName: String = textInputOrganizationName.text.toString()
            val organizationEmail: String = textInputOrganizationEmail.text.toString()
            val organizationDescription: String = textInputOrganizationDescription.text.toString()
            val organizationRegion : String = regionSpinner.selectedItem.toString()

            if(organizationName != "" && organizationEmail != "" && organizationRegion != "") {
                val handler = Handler(Looper.getMainLooper())
                handler.post {
                    val field = arrayOfNulls<String>(4)
                    field[0] = "name"
                    field[1] = "email"
                    field[2] = "info"
                    field[3] = "region"
                    //Creating array for data
                    val data = arrayOfNulls<String>(4)
                    data[0] = organizationName
                    data[1] = organizationEmail
                    data[2] = organizationDescription
                    data[3] = organizationRegion
                    val putData = PutData(
                        "http://192.168.0.103/LoginRegister/CreateOrganization.php",
                        "POST",
                        field,
                        data
                    )
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            val result = putData.result
                            if (result.equals("Creation Success")) {
                                val intent = Intent(this, Content::class.java)
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