package com.example.vgestas.sensor_pong

import android.arch.lifecycle.ViewModel


class ScoreViewModel:ViewModel()
{

}


sealed class ScoreEvent
class ScoreOk(val model: GravityModel) : ScoreEvent()
class ScoreError(val error: String) : ScoreEvent()