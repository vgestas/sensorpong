package com.example.vgestas.sensor_pong

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_game.*
import android.media.MediaPlayer
import android.os.CountDownTimer
import java.util.concurrent.TimeUnit
import android.graphics.Point



class GameActivity : AppCompatActivity() {
    private val viewModel: GravityViewModel by lazy {
        GravityViewModel(application)
    }


    private lateinit var mp: MediaPlayer
    private  var scoreCourant:Int = 0
    private var toucheHaut:Boolean = false
    private var toucheBas:Boolean = false
    var width: Int = 0
    var height: Int = 0

    var timer: CountDownTimer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        mp = MediaPlayer.create(this, R.raw.musique)

        BackgroundMusic()

        scoreCourant = 0
        score.text = scoreCourant.toString()

        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        width = size.y
        height = size.x


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
        timer = object : CountDownTimer(60 * 1000, 1000)
        {
            override fun onFinish(){
                finishGame()
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
        rotationView.translationX = (mainContainer.width / 2 * -viewModel.model.xAxisTranslation)
        if(viewModel.model.xAxisTranslation <= -0.75)
        {
            scoreCourant --
            score.text = scoreCourant.toString()
        }
        else if(viewModel.model.xAxisTranslation >= 0.75)
        {
            scoreCourant --
            score.text = scoreCourant.toString()
        }
    }

    private fun setTranslationY() {
        rotationView.translationY = (mainContainer.height / 2 * viewModel.model.yAxisTranslation)
        if(viewModel.model.yAxisTranslation >= 0.75 )
        {
            if(!toucheBas)
            {
                scoreCourant = scoreCourant + 100
                score.text = scoreCourant.toString()
                toucheBas = true
                toucheHaut = false
            }

        }
        else if(viewModel.model.yAxisTranslation <= -0.75)
        {
            if(!toucheHaut)
            {
                scoreCourant = scoreCourant + 100
                score.text = scoreCourant.toString()
                toucheHaut = true
                toucheBas = false
            }

        }
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
