package com.example.vgestas.sensor_pong

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
}