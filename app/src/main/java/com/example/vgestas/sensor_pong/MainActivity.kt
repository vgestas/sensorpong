package com.example.vgestas.sensor_pong

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.dialog_rules.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val context = this

        val db = DataBaseHandler(context)

        val list:MutableList<Score> = db.getFirstScore()

        if(list.size > 0)
        {
            firstScore.text = list.get(0).score.toString()
            userNameScoreOne.text =list.get(0).username

            try {
                secondScore.text = list.get(1).score.toString()
                userNameScoreTwo.text = list.get(1).username
            } catch(e:Exception) {
                secondScore.visibility = TextView.INVISIBLE
                userNameScoreTwo.visibility = TextView.INVISIBLE
            }

            try {
                thirdScore.text = list.get(2).score.toString()
                userNameScoreThree.text = list.get(2).username
            } catch(e:Exception) {
                thirdScore.visibility = TextView.INVISIBLE
                userNameScoreThree.visibility = TextView.INVISIBLE
            }
        }

        button.setOnClickListener({
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            finish()
        })

        help.setOnClickListener({
            viewRules()
        })
    }

    private fun viewRules()
    {
        val dialog = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_rules, null)
        dialog.setView(dialogView)
        dialog.setCancelable(false)
        dialog.setPositiveButton("Ok", { dialogInterface: DialogInterface, i: Int -> })
        val customDialog = dialog.create()
        customDialog.show()
        customDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener({
            customDialog.dismiss()
        })
    }

}
