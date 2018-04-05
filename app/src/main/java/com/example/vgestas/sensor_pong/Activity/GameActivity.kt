package com.example.vgestas.sensor_pong.Activity

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Vibrator
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View.GONE
import com.example.vgestas.sensor_pong.*
import com.example.vgestas.sensor_pong.ViewModel.*
import kotlinx.android.synthetic.main.activity_game.*
import java.util.concurrent.TimeUnit


class GameActivity : AppCompatActivity() {

    private lateinit var mp: MediaPlayer

    //Allows the player to go up and down alternately
    private var touch_up: Boolean = false

    var timer: CountDownTimer? = null
    var timerBeforeParty: CountDownTimer? = null

    private val viewModel: GravityViewModel by lazy {
        GravityViewModel(application)
    }

    private val viewModelScore: ScoreViewModel by lazy {
        ScoreViewModel(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        mp = MediaPlayer.create(this, R.raw.audio)

        BackgroundMusic()

        score.text = viewModelScore.model.score.toString()

        startTimerBeforeParty()

        viewModelScore.events.observe(this, Observer<ScoreEvent> { event ->
            when (event) {
                is ScoreOk -> refreshScore()
                is ScoreError -> showError("Score error !")
            }
        })

        viewModel.events.observe(this, Observer<GravityEvent?> { event ->
            when (event) {
                is GravityOk -> refreshUi()
                is GravityError -> showError("Error!")
            }
        })
    }

    private fun startTimerParty() {
        viewModel.startRegisterListener()

        timer = object : CountDownTimer(15 * 1000, 1000) {
            override fun onFinish() {
                finishGame()
            }

            override fun onTick(millisUntilFinished: Long) {

                timerScreen.text = (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)).toString()
                if (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) <= 5) {
                    timerScreen.setTextColor(Color.parseColor("#F70101"))
                    val vibratorService = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    vibratorService.vibrate(500)
                }
            }
        }.start()
    }

    private fun startTimerBeforeParty() {
        timerBeforeParty = object : CountDownTimer(3 * 1000, 1000) {
            override fun onFinish() {
                startTimerParty()
                timerStartParty.visibility = GONE
            }

            override fun onTick(millisUntilFinished: Long) {

                timerStartParty.text = (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)).toString()
            }
        }.start()
    }

    fun BackgroundMusic() {
        mp.start()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopRegisterListener()
    }

    private fun refreshUi() {
        setTranslation()
    }

    private fun refreshScore() {
        score.text = viewModelScore.model.score.toString()
    }

    private fun showError(message: String) {
        Log.d("Error", message)
    }

    private fun setTranslationX() {
        rotationView.translationX = (mainContainer.width / 2 * -viewModel.model.xAxisTranslation)
        if (viewModel.model.xAxisTranslation <= -0.75) {
            viewModelScore.updateScore(-1)
        } else if (viewModel.model.xAxisTranslation >= 0.75) {
            viewModelScore.updateScore(-1)
        }
    }

    private fun setTranslationY() {
        rotationView.translationY = (mainContainer.height / 2 * viewModel.model.yAxisTranslation)
        if (viewModel.model.yAxisTranslation >= 0.75) {
            if (touch_up) {
                viewModelScore.updateScore(100)
                rotationView.setBackgroundResource(R.drawable.ballsp_min)
                touch_up = false
            }
        } else if (viewModel.model.yAxisTranslation <= -0.75) {
            if (!touch_up) {
                viewModelScore.updateScore(100)
                rotationView.setBackgroundResource(R.drawable.ballsp_inv_min)
                touch_up = true
            }
        }
    }

    private fun setTranslation() {
        setTranslationX()
        setTranslationY()
    }

    private fun finishGame() {
        mp.stop()

        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra("scoreParty", score.text.toString())
        }
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
    }
}
