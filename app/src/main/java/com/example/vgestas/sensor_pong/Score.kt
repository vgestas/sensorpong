package com.example.vgestas.sensor_pong

import android.content.Context
import java.security.AccessControlContext
import java.text.SimpleDateFormat
import java.util.*

class Score
{
    var id:Int = 0

    var score:Int = 0

    var username:String=""

    var date:String=""


    constructor(score:Int, username:String, date:String)
    {
        this.score = score
        this.username = username
        this.date = date

    }

    constructor(){
    }

    fun insertUser(context:Context, score:Int)
    {
        var db = DataBaseHandler(context)
        date = SimpleDateFormat("dd/MM/yyyy").format(Date())
        var score = Score(score, username, date)
        db.insertData(score)
        db.close()
    }


    fun updateScore(valeur:Int)
    {
        score = score + valeur
    }

    fun updateUsername(username:String)
    {
        this.username = username
    }

    fun setScoreParty(score:Int)
    {
        this.score = score
    }
}