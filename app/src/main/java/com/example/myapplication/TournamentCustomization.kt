package com.example.myapplication

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.*
import com.google.android.material.textfield.TextInputEditText
import com.vishnusivadas.advanced_httpurlconnection.PutData
import java.util.*

@Suppress("NAME_SHADOWING")
class TournamentCustomization : TournamentGameChoose(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    @SuppressLint("MissingInflatedId", "ResourceAsColor", "ResourceType")
    var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    private var savedDay = 0
    private var savedMonth = 0
    private var savedYear = 0
    private var savedHour = 0
    private var savedMinute = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tournament_customization)
        val setTournamentStartData: Button = findViewById(R.id.setTournamentStartDate)
        setTournamentStartData.setOnClickListener{
            getDateTimeCalendar()
            pickDate()
        }
        val gameChosen = intent.extras
        val onceEliminationToggleButton = findViewById<ToggleButton>(R.id.onceEliminationToggleButton)
        val swissSystemToggleButton = findViewById<ToggleButton>(R.id.swissSystemToggleButton)
        val tournamentName = findViewById<TextInputEditText>(R.id.tournamentName)
        val tournamentGameModeSpinner = findViewById<Spinner>(R.id.tournamentGameMode)
        val tournamentTeamsCountSpinner = findViewById<Spinner>(R.id.tournamentTeamsCount)
        val tournamentInfo = findViewById<TextInputEditText>(R.id.tournamentDescription)
        val tournamentRules = findViewById<TextInputEditText>(R.id.tournamentRules)
        val tournamentRegion = findViewById<Spinner>(R.id.tournamentRegion)
        val createTournamentButton = findViewById<Button>(R.id.createTournamentButton)

        val teamsCountValues : Array<Int> = arrayOf(2, 4, 8, 16, 32, 64)

        ArrayAdapter(this, android.R.layout.simple_spinner_item, teamsCountValues).also {
                adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                tournamentTeamsCountSpinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(this, R.array.valorantGameModes,android.R.layout.simple_spinner_item).also {
                adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            tournamentGameModeSpinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(this, R.array.regions,android.R.layout.simple_spinner_item).also {
                adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            tournamentRegion.adapter = adapter
        }

        createTournamentButton.setOnClickListener{
            val tournamentName: String = tournamentName.text.toString()
            val tournamentGame : String? = gameChosen?.getString("game", "wtf")
            val tournamentGameMode : String = tournamentGameModeSpinner.selectedItem.toString()
            val tournamentTeamsCount : String = tournamentTeamsCountSpinner.selectedItem.toString()
            val tournamentInfo: String = tournamentInfo.text.toString()
            val tournamentRules: String = tournamentRules.text.toString()
            val tournamentRegion : String = tournamentRegion.selectedItem.toString()
            val tournamentFormat = if(onceEliminationToggleButton.isChecked){
                onceEliminationToggleButton.textOn.toString()
            }else {
                swissSystemToggleButton.textOn.toString()
            }
            var tournamentDateText = findViewById<TextView>(R.id.dateText)
            val tournamentStartDate = tournamentDateText.text.toString()

            if(tournamentName != "" && tournamentFormat!="" && tournamentGameMode != "" && tournamentRegion != "" && tournamentStartDate!="0000-00-00" && tournamentStartDate!="" && tournamentStartDate!=null) {
                val sharedPreferences = this.getSharedPreferences("loginInfo",
                    Context.MODE_PRIVATE)
                val tournamentCreatorId = sharedPreferences.getInt("userId", 0)
                val tournamentPrizes = "no"
                val handler = Handler(Looper.getMainLooper())
                handler.post {
                    val field = arrayOfNulls<String>(11)
                    field[0] = "creator_id"
                    field[1] = "name"
                    field[2] = "info"
                    field[3] = "region"
                    field[4] = "start_date"
                    field[5] = "prizes"
                    field[6] = "game"
                    field[7] = "game_mode"
                    field[8] = "format"
                    field[9] = "teams_count"
                    field[10] = "rules"
                    //Creating array for data
                    val data = arrayOfNulls<String>(11)
                    data[0] = tournamentCreatorId.toString()
                    data[1] = tournamentName
                    data[2] = tournamentInfo
                    data[3] = tournamentRegion
                    data[4] = tournamentStartDate
                    data[5] = tournamentPrizes
                    data[6] = tournamentGame
                    data[7] = tournamentGameMode
                    data[8] = tournamentFormat
                    data[9] = tournamentTeamsCount
                    data[10] = tournamentRules
                    val putData = PutData(
                        "http://www.srv184174.hoster-test.ru/cybernament/scripts/createtournament.php ",
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
        onceEliminationToggleButton.setOnClickListener{
            if(!onceEliminationToggleButton.isChecked){
                swissSystemToggleButton.isChecked = true
                onceEliminationToggleButton.isChecked = false
            }
            else{
                onceEliminationToggleButton.isChecked = true
                swissSystemToggleButton.isChecked = false
            }
        }

        swissSystemToggleButton.setOnClickListener{
            if(!swissSystemToggleButton.isChecked){
                swissSystemToggleButton.isChecked = false
                onceEliminationToggleButton.isChecked = true
            }
            else{
                swissSystemToggleButton.isChecked = true
                onceEliminationToggleButton.isChecked = false
            }
        }
    }

    private fun getDateTimeCalendar(){
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
    }

    private fun pickDate(){
            getDateTimeCalendar()
            DatePickerDialog(this, this, year, month, day).show()
    }
    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month + 1
        savedYear = year
        getDateTimeCalendar()

        TimePickerDialog(this, this, hour, minute, true).show()
    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute
        var tournamentDateText = findViewById<TextView>(R.id.dateText)

        tournamentDateText.text = "$savedDay-$savedMonth-$savedYear\n$savedHour:$savedMinute"
    }
}