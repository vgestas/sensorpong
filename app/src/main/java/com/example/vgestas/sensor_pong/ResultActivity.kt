package com.example.vgestas.sensor_pong

import android.annotation.TargetApi
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

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
}