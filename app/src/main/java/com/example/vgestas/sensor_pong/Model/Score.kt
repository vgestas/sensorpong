package com.example.vgestas.sensor_pong.Model

import android.content.Context
import com.example.vgestas.sensor_pong.Database.DataBaseHandler
import java.text.SimpleDateFormat
import java.util.*

class Score {
    var id: Int = 0
    var score: Int = 0
    var username: String = ""
    var date: String = ""


    constructor(score: Int, username: String, date: String) {
        this.score = score
        this.username = username
        this.date = date
    }

    constructor() {
        score = 0
    }

    //player insertion
    fun insertUser(context: Context, score: Int) {
        var db = DataBaseHandler(context)
        date = SimpleDateFormat("dd/MM/yyyy").format(Date())
        var score = Score(score, username, date)
        db.insertData(score)
        db.close()
    }


    //Updating score
    fun updateScore(valeur: Int) {
        score = score + valeur
    }

    //Updating username
    fun updateUsername(username: String) {
        this.username = username
    }

    //Set the score of the game that has just been played
    fun setScoreParty(score: Int) {
        this.score = score
    }
}