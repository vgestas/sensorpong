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

    fun selectScoreList(): MutableList<Score> {
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

        result.close()
        db.close()

        return listScore
    }

        fun selectScoreListByUsername(username:String): MutableList<Score>
        {
            var listScoreUser: MutableList<Score> = ArrayList()
            val db = this.readableDatabase
            val query = "Select * from " + TABLE_NAME + " where " + COL_USERNAME + " LIKE '" + username + "'"
            val result = db.rawQuery(query, null)

            if(result.moveToFirst())
            {
                do
                {
                    var score = Score()
                    score.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                    score.username = result.getString(result.getColumnIndex(COL_USERNAME))
                    score.score = result.getString(result.getColumnIndex(COL_SCORE)).toInt()
                    score.date = result.getString(result.getColumnIndex(COL_DATE))
                    listScoreUser.add(score)
                } while (result.moveToNext())
            }

        result.close()
        db.close()

        return listScoreUser
    }

    fun updateDate()
    {
        val db = this.writableDatabase
        val query = "Select * from " + TABLE_NAME
        val result = db.rawQuery(query, null)

        if(result.moveToFirst())
        {
            do
            {
                var cv = ContentValues()
                cv.put(COL_SCORE, result.getInt(result.getColumnIndex(COL_SCORE)+2))
                db.update(TABLE_NAME, cv, COL_ID + "=? AND "+ COL_USERNAME + "=?",
                        arrayOf(result.getString(result.getColumnIndex(COL_ID)),
                                result.getString(result.getColumnIndex(COL_USERNAME))))

            } while (result.moveToNext())
        }

        result.close()
        db.close()
    }

    fun getFirstScore() : MutableList<Score>
    {
        var listBestScore:MutableList<Score> = ArrayList()
        var listScore:MutableList<Score> = ArrayList()
        val db = this.readableDatabase
        val query = "Select " + COL_USERNAME + ", " + COL_SCORE + " From " + TABLE_NAME
        val result = db.rawQuery(query, null)

        if(result.moveToFirst())
        {
            do
            {
                var score = Score()
                score.username = result.getString(result.getColumnIndex(COL_USERNAME))
                score.score = result.getString(result.getColumnIndex(COL_SCORE)).toInt()
                listScore.add(score)
            } while (result.moveToNext())

            listScore.sortByDescending { it.score }



            var index = 0
            var max = 3
            if(listScore.size < 3)
                max = listScore.size



            listBestScore = listScore.subList(0,max)


        }

        result.close()
        db.close()
        return listBestScore
    }
}