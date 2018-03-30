package com.example.vgestas.sensor_pong

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DATABASE_NAME = "SensorPongBD"
val TABLE_NAME = "ScoreTable"
val COL_ID = "ID"
val COL_SCORE = "Score"
val COL_USERNAME = "Username"
val COL_DATE = "Date"

class DataBaseHandler (private val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1)
{
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

    fun insertData(score: Score)
    {
        val db = this.writableDatabase
        var contentValues = ContentValues()
        contentValues.put(COL_SCORE, score.score)
        contentValues.put(COL_USERNAME, score.username)
        contentValues.put(COL_DATE, score.date)
        var result  = db.insert(TABLE_NAME, null ,contentValues)
        if(result == -1.toLong())
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show()
        }
        else
        {
            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()
        }
    }

    fun selectUsername(): String
    {
        var username: String = ""
        val db = this.readableDatabase
        val query = "Select username from " + TABLE_NAME + " where username=\"vinc\""
        val result = db.rawQuery(query, null)

        if(result.count != -1)
        {
            result.moveToFirst()
            username = result.getString(0)
        }

        result.close()
        db.close()

        return username
    }
}