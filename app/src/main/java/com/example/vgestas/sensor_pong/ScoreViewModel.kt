package com.example.vgestas.sensor_pong

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context


class ScoreViewModel(application: Application, val model: Score = Score()) : AndroidViewModel(application)
{
    val events: MutableLiveData<ScoreEvent> = MutableLiveData()


    fun updateScore(valeur:Int)
    {
        model.updateScore(valeur)
        events.value = ScoreOk(model)
    }

    fun updateUsername(username:String)
    {
        model.updateUsername(username)
        events.value = ScoreOk(model)
    }

    fun insertUser(context: Context, score:Int)
    {
        model.insertUser(context, score)
    }

    fun setScoreParty(score:Int)
    {
        model.setScoreParty(score)
    }
}


sealed class ScoreEvent
class ScoreOk(val model: Score) : ScoreEvent()
class ScoreError(val error: String) : ScoreEvent()