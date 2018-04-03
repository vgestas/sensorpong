package com.example.vgestas.sensor_pong

import android.content.Context
import java.security.AccessControlContext
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
        score = 0
    }

    fun getScore(context: Context, username: String)
    {
        var db = DataBaseHandler(context)
        var listScore : MutableList<Score> = ArrayList()
        listScore = db.selectScoreListByUsername(username)
        db.close()
    }

    fun insertUsername(context:Context, username: String, score: Int)
    {
        var db = DataBaseHandler(context)
        val calendar = Calendar.getInstance() as Calendar
        var date = calendar.time as Date
        var score = Score(score, username, date.toString())
        db.insertData(score)
        db.close()
    }

    fun getFirstScore(context: Context)
    {
        var db = DataBaseHandler(context)
    }

    fun updateScore(valeur:Int)
    {
        score = score + valeur
    }
}