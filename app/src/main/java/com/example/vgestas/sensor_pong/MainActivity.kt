package com.example.vgestas.sensor_pong

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val context = this

       // val score = Score(120, "vinc", "10/10/2010")
        var db = DataBaseHandler(context)
        //db.insertData(score)

        var username = db.selectUsername()
        textview.setText(username)

    }
}
