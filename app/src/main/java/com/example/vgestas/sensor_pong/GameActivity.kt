package com.example.vgestas.sensor_pong

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_game.*
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.widget.Toast
import java.util.concurrent.TimeUnit


class GameActivity : AppCompatActivity() {
    private val viewModel: GravityViewModel by lazy {
        //        ViewModelProvider.of(this, vmFactory(application)).create(GVM::class.java)
        GravityViewModel(application)
    }


    private lateinit var mp: MediaPlayer

    var timer: CountDownTimer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        mp = MediaPlayer.create(this, R.raw.musique)

        BackgroundMusic()

        score.setText("0")

        startTimer()


        viewModel.events.observe(this, Observer<GravityEvent?> {
            event ->
            when (event) {
                is GravityOk -> refreshUi()
                is GravityError -> showError("Error!")
            }
        })
    }

    private fun startTimer()
    {
        timer = object : CountDownTimer(15 * 1000, 1000)
        {
            override fun onFinish(){
                finishGame();
            }

            override fun onTick(millisUntilFinished: Long) {

                timerScreen.text = (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)).toString()
            }
        }.start()
    }

    fun BackgroundMusic()
    {
        mp.start()
    }

    override fun onResume() {
        super.onResume()
        viewModel.startRegisterListener()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopRegisterListener()
    }

    private fun refreshUi() {
        setTranslation()
    }

    private fun showError(message: String) {
        Log.d("Error", message)
    }

    private fun setTranslationX() {
        rotationView.translationX = (mainContainer.width / 2 * viewModel.model.xAxisTranslation)
    }

    private fun setTranslationY() {
        rotationView.translationY = (mainContainer.height / 2 * -viewModel.model.yAxisTranslation)

    }

    private fun setTranslation() {
        setTranslationX()
        setTranslationY()
    }

    private fun finishGame(){
        mp.stop()

        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra("scoreParty", score.text.toString())
        }
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mp.stop()
    }
}
