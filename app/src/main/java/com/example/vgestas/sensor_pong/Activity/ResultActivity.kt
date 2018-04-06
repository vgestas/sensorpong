package com.example.vgestas.sensor_pong.Activity

import android.annotation.TargetApi
import android.arch.lifecycle.Observer
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.vgestas.sensor_pong.*
import com.example.vgestas.sensor_pong.Adapter.RankingAdapter
import com.example.vgestas.sensor_pong.Database.DataBaseHandler
import com.example.vgestas.sensor_pong.Model.Score
import com.example.vgestas.sensor_pong.ViewModel.ScoreError
import com.example.vgestas.sensor_pong.ViewModel.ScoreEvent
import com.example.vgestas.sensor_pong.ViewModel.ScoreOk
import com.example.vgestas.sensor_pong.ViewModel.ScoreViewModel
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    private val viewModelScore: ScoreViewModel by lazy {
        ScoreViewModel(application)
    }

    val viewAdapter: RankingAdapter by lazy {
        RankingAdapter()
    }

    var rankingUser: Int = 0

    @TargetApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // Watch when username is entered
        viewModelScore.events.observe(this, Observer<ScoreEvent> { event ->
            when (event) {
                is ScoreOk -> refreshUsername()
                is ScoreError -> showError("Score error !")
            }
        })

        //Retrieving the score of the last game
        viewModelScore.setScoreParty(intent.getStringExtra("scoreParty").toInt())
        scoreParty.text = viewModelScore.model.score.toString()

        // Opens an alert to enter a username
        openAlertUsername()

        //Initialize the ranking and display it
        val db = DataBaseHandler(this)
        val list: MutableList<Score> = db.getFirstScore()
        if (list.size > 0) {
            firstScoreRanking.text = list.get(0).score.toString()
            usernameFirst.text = list.get(0).username

            try {
                secondScoreRanking.text = list.get(1).score.toString()
                usernameSecond.text = list.get(1).username
            } catch (e: Exception) {
                secondScoreRanking.visibility = TextView.INVISIBLE
                usernameSecond.visibility = TextView.INVISIBLE
            }

            try {
                thirdScoreRanking.text = list.get(2).score.toString()
                usernameThird.text = list.get(2).username
            } catch (e: Exception) {
                thirdScoreRanking.visibility = TextView.INVISIBLE
                usernameThird.visibility = TextView.INVISIBLE
            }
        }


        //Back MainActivity
        home.setOnClickListener({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        })

        //Back GameActivity
        replay.setOnClickListener({
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            finish()
        })


        //displays an alert containing the entire ranking
        layoutRanking.setOnClickListener({

            val dialog = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_ranking, null)
            val recycler = dialogView.findViewById<RecyclerView>(R.id.recycler)

            recycler.apply {
                layoutManager = LinearLayoutManager(dialog.context)
                recycler.adapter = viewAdapter
            }

            val listScore: MutableList<Score> = db.getAllScore()
            viewAdapter.setScore(listScore.toMutableList())
            viewAdapter.setRanking(rankingUser)

            dialog.setView(dialogView)
            dialog.setCancelable(false)
            dialog.setTitle("CLASSEMENT")
            dialog.setPositiveButton("Ok", { dialogInterface: DialogInterface, i: Int -> })
            val customDialog = dialog.create()
            customDialog.show()
            customDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener({
                customDialog.dismiss()
            })
        })
    }

    // Displays an alert to enter the username
    private fun openAlertUsername() {
        val dialog = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.custom_dialog, null)
        val usernameEdit = dialogView.findViewById<EditText>(R.id.usernameEdit)
        dialog.setView(dialogView)
        dialog.setTitle("Saisissez votre pseudo !")
        dialog.setCancelable(false)
        dialog.setPositiveButton("Validate", { dialogInterface: DialogInterface, i: Int -> })
        val customDialog = dialog.create()
        customDialog.show()
        customDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener({

            val msg = usernameEdit.text.trim().toString()
            if (msg.isEmpty()) {
                Toast.makeText(this, "Saisir un pseudo svp", Toast.LENGTH_LONG).show()
            } else {
                viewModelScore.updateUsername(usernameEdit.text.toString())
                viewModelScore.insertUser(this, scoreParty.text.toString().toInt())
                val db = DataBaseHandler(this)
                rankingUser = db.getRankingParty()
                rankingParty.text = rankingUser.toString() + "/" + (db.getAllScore().size).toString()
                customDialog.dismiss()
            }
        })
    }

    private fun showError(message: String) {
        Log.d("Error", message)
    }

    // Refreshes the username on the UI
    private fun refreshUsername() {
        usernameParty.text = viewModelScore.model.username
    }

    //Back to MainAtivity
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}