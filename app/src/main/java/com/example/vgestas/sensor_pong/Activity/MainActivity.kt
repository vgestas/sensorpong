package com.example.vgestas.sensor_pong.Activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.example.vgestas.sensor_pong.Database.DataBaseHandler
import com.example.vgestas.sensor_pong.R
import com.example.vgestas.sensor_pong.Model.Score
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialize the ranking and display it
        val context = this
        val db = DataBaseHandler(context)
        val list: MutableList<Score> = db.getFirstScore()
        if (list.size > 0) {
            firstScore.text = list.get(0).score.toString()
            userNameScoreOne.text = list.get(0).username

            try {
                secondScore.text = list.get(1).score.toString()
                userNameScoreTwo.text = list.get(1).username
            } catch (e: Exception) {
                secondScore.visibility = TextView.INVISIBLE
                userNameScoreTwo.visibility = TextView.INVISIBLE
            }

            try {
                thirdScore.text = list.get(2).score.toString()
                userNameScoreThree.text = list.get(2).username
            } catch (e: Exception) {
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

    //Displays an alert containing rules and information
    private fun viewRules() {

        //We recover the strings to display in the String file
        var index: Int = 0
        val listTitle: ArrayList<String> = arrayListOf()
        listTitle.add(getString(R.string.presentation))
        listTitle.add(getString(R.string.point_title))
        listTitle.add(getString(R.string.more_information_title))
        listTitle.add(getString(R.string.credit_title))

        val listText: ArrayList<String> = arrayListOf()
        listText.add(getString(R.string.presentation_text))
        listText.add(getString(R.string.point_text))
        listText.add(getString(R.string.more_information_text))
        listText.add(getString(R.string.credit_text))

        val dialog = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_rules, null)
        val button_left = dialogView.findViewById<ImageView>(R.id.button_left)
        val button_right = dialogView.findViewById<ImageView>(R.id.button_right)
        val ruleTitle = dialogView.findViewById<TextView>(R.id.ruleTitle)
        val ruleText = dialogView.findViewById<TextView>(R.id.ruleText)
        button_left.setOnClickListener({
            if (index == 0) {
                index = listTitle.size - 1

            } else {
                index--
            }

            ruleTitle.text = listTitle.get(index).toString()
            ruleText.text = listText.get(index).toString()

        })

        button_right.setOnClickListener({
            if (index == listTitle.size - 1) {
                index = 0
            } else {
                index++
            }

            ruleTitle.text = listTitle.get(index).toString()
            ruleText.text = listText.get(index).toString()
        })
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
