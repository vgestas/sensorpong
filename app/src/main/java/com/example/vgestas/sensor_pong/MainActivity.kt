package com.example.vgestas.sensor_pong

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val context = this

        val db = DataBaseHandler(context)

        val list:MutableList<Score> = db.getFirstScore()

        firstScore.text = list.get(0).score.toString()
        secondScore.text = list.get(1).score.toString()
        thirdScore.text = list.get(2).score.toString()
        userNameScoreOne.text =list.get(0).username
        userNameScoreTwo.text = list.get(1).username
        userNameScoreThree.text = list.get(2).username



        //var username = db.selectUsername()
       // textview.setText(username)

        button.setOnClickListener({
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        })

    }

}
