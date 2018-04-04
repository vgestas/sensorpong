package com.example.vgestas.sensor_pong

import android.annotation.TargetApi
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity: AppCompatActivity()
{
    private lateinit var username: String
    private lateinit var date:String
    private lateinit var score:String

    @TargetApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        score = intent.getStringExtra("scoreParty")

        date = "14/12/2018"

        openAlert()

        val context = this

        val db = DataBaseHandler(context)

        val list:MutableList<Score> = db.getFirstScore()

        if(list.size != 0)
        {
            firstScoreRanking.text = list.get(0).score.toString()
            usernameFirst.text = list.get(0).username
           /* secondScoreRanking.text = list.get(1).score.toString()
            usernameSecond.text =list.get(1).username
            thirdScoreRanking.text = list.get(2).score.toString()
            usernameThird.text = list.get(2).username*/
        }


        scoreParty.text = score

        home.setOnClickListener({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        })

        replay.setOnClickListener({
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            finish()
        })

    }

    private fun openAlert()
    {
        val dialog = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.custom_dialog, null)
        val usernameEdit = dialogView.findViewById<EditText>(R.id.usernameEdit)
        dialog.setView(dialogView)
        dialog.setCancelable(false)
        dialog.setPositiveButton("Validate", { dialogInterface: DialogInterface, i: Int -> })
        val customDialog = dialog.create()
        customDialog.show()
        customDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener({
            username = usernameEdit.text.toString()
            usernameParty.text = username
            addUser(username)
            customDialog.dismiss()
        })
    }

    private fun addUser(username:String)
    {
        val db = DataBaseHandler(this)
        val score = Score(score.toInt(), username, date)
        db.insertData(score)

        Toast.makeText(this, "Insertion effectuée", Toast.LENGTH_LONG).show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}