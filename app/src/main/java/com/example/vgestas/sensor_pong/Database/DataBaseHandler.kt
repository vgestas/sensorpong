package com.example.vgestas.sensor_pong.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.vgestas.sensor_pong.Model.Score

val DATABASE_NAME = "SensorPongBD"
val TABLE_NAME = "ScoreTable"
val COL_ID = "ID"
val COL_SCORE = "Score"
val COL_USERNAME = "Username"
val COL_DATE = "Date"

class DataBaseHandler(private val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    //Creating the table
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_SCORE + " INTEGER," +
                COL_USERNAME + " VARCHAR(5), " +
                COL_DATE + " VARCHAR(10))"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    //inserting data
    fun insertData(score: Score) {
        val db = this.writableDatabase
        var contentValues = ContentValues()
        contentValues.put(COL_SCORE, score.score)
        contentValues.put(COL_USERNAME, score.username)
        contentValues.put(COL_DATE, score.date)
        var result = db.insert(TABLE_NAME, null, contentValues)
        if (result == -1.toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()
        }
        db.close()
    }

    //Score Recovery
    fun getAllScore(): MutableList<Score> {
        var listScore: MutableList<Score> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME
        val result = db.rawQuery(query, null)

        if (result.moveToFirst()) {
            do {
                var score = Score()
                score.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                score.username = result.getString(result.getColumnIndex(COL_USERNAME))
                score.score = result.getString(result.getColumnIndex(COL_SCORE)).toInt()
                score.date = result.getString(result.getColumnIndex(COL_DATE))
                listScore.add(score)
            } while (result.moveToNext())
        }

        listScore.sortByDescending { it.score }

        result.close()
        db.close()

        return listScore
    }


    //Retrieving the first 3 scores to display them on the home page and on the result page
    fun getFirstScore(): MutableList<Score> {
        var listBestScore: MutableList<Score> = ArrayList()
        var listScore: MutableList<Score> = ArrayList()
        val db = this.readableDatabase
        val query = "Select " + COL_USERNAME + ", " + COL_SCORE + " From " + TABLE_NAME
        val result = db.rawQuery(query, null)

        if (result.moveToFirst()) {
            do {
                var score = Score()
                score.username = result.getString(result.getColumnIndex(COL_USERNAME))
                score.score = result.getString(result.getColumnIndex(COL_SCORE)).toInt()
                listScore.add(score)
            } while (result.moveToNext())

            listScore.sortByDescending { it.score }

            var max = 3
            if (listScore.size < 3)
                max = listScore.size

            listBestScore = listScore.subList(0, max)
        }

        result.close()
        db.close()
        return listBestScore
    }

    //Recovery of the ranking of the game that has just been played
    fun getRankingParty(): Int {
        val listScore: MutableList<Score> = this.getAllScore()
        val db = this.readableDatabase
        val query = "Select Max(${COL_ID}) from ${TABLE_NAME}"
        val result = db.rawQuery(query, null)
        var idParty: Int = 0
        if (result.moveToFirst()) {
            idParty = result.getString(result.getColumnIndex("Max(ID)")).toInt()
        }

        var rankingParty: Int = 0

        listScore.sortByDescending { it.score }

        for (currentScore: Score in listScore) {
            if (currentScore.id == idParty) {
                rankingParty = listScore.indexOf(currentScore) + 1
                break
            }
        }

        result.close()
        db.close()
        return rankingParty
    }
}