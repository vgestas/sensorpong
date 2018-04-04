package com.example.vgestas.sensor_pong

import android.annotation.TargetApi
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_result.*
import android.arch.lifecycle.Observer
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class ResultActivity: AppCompatActivity()
{
    private val viewModelScore: ScoreViewModel by lazy{
        ScoreViewModel(application)
    }

    val viewAdapter: RankingAdapter by lazy {
        RankingAdapter()
    }

    @TargetApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        viewModelScore.setScoreParty(intent.getStringExtra("scoreParty").toInt())


        openAlert()

        val context = this

        val db = DataBaseHandler(context)

        val list:MutableList<Score> = db.getFirstScore()

        if(list.size > 0)
        {
            firstScoreRanking.text = list.get(0).score.toString()
            usernameFirst.text = list.get(0).username

            try{
                secondScoreRanking.text = list.get(1).score.toString()
                usernameSecond.text =list.get(1).username
            } catch(e:Exception) {
                secondScoreRanking.visibility = TextView.INVISIBLE
                usernameSecond.visibility = TextView.INVISIBLE
            }

            try {
                thirdScoreRanking.text = list.get(2).score.toString()
                usernameThird.text = list.get(2).username
            } catch (e:Exception) {
                thirdScoreRanking.visibility = TextView.INVISIBLE
                usernameThird.visibility = TextView.INVISIBLE
            }
        }


        scoreParty.text = viewModelScore.model.score.toString()

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

        viewModelScore.events.observe(this, Observer<ScoreEvent> {
            event->
            when(event){
                is ScoreOk -> refreshUsername()
                is ScoreError -> showError("Score error !")
            }
        })

        layoutClassemnt.setOnClickListener({

            val dialog = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_ranking, null)
            val recycler = dialogView.findViewById<RecyclerView>(R.id.recycler)

            recycler.apply {
                layoutManager = LinearLayoutManager(dialog.context)
                recycler.adapter = viewAdapter
            }

            val listScore:MutableList<Score> = db.getAllScore()
            viewAdapter.setScore(listScore.toMutableList())

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
            viewModelScore.updateUsername(usernameEdit.text.toString())
            viewModelScore.insertUser(this, scoreParty.text.toString().toInt())
            customDialog.dismiss()
        })
    }

    private fun showError(message: String) {
        Log.d("Error", message)
    }

    private fun refreshUsername()
    {
        usernameParty.text = viewModelScore.model.username
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}