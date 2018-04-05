package com.example.vgestas.sensor_pong.ViewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.example.vgestas.sensor_pong.Model.Score


class ScoreViewModel(application: Application, val model: Score = Score()) : AndroidViewModel(application) {
    val events: MutableLiveData<ScoreEvent> = MutableLiveData()


    //Update the score on the UI
    fun updateScore(value: Int) {
        model.updateScore(value)
        events.value = ScoreOk(model)
    }

    //Update the username on the UI
    fun updateUsername(username: String) {
        model.updateUsername(username)
        events.value = ScoreOk(model)
    }

    //Insert user into database
    fun insertUser(context: Context, score: Int) {
        model.insertUser(context, score)
    }

    //Set the score into model
    fun setScoreParty(score: Int) {
        model.setScoreParty(score)
    }
}


sealed class ScoreEvent
class ScoreOk(val model: Score) : ScoreEvent()
class ScoreError(val error: String) : ScoreEvent()