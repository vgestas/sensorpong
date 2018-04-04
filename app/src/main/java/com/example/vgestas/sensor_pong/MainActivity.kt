package com.example.vgestas.sensor_pong

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.EditText
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

        if(list.size != 0)
        {
            firstScore.text = list.get(0).score.toString()
          /*  secondScore.text = list.get(1).score.toString()
            thirdScore.text = list.get(2).score.toString()*/
            userNameScoreOne.text =list.get(0).username
            /*userNameScoreTwo.text = list.get(1).username
            userNameScoreThree.text = list.get(2).username*/
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
