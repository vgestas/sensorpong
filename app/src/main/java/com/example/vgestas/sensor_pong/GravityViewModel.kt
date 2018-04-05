package com.example.vgestas.sensor_pong

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class GravityViewModel(application: Application, val model: GravityModel = GravityModel()) : AndroidViewModel(application), SensorEventListener {
    private val sensorManager: SensorManager by lazy {
        application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
    private val gravity: Sensor by lazy {
        sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
    }
    val events: MutableLiveData<GravityEvent> = MutableLiveData()

    init {
        events.value = GravityOk(model)
    }

    fun updateGravity(xGravity: Float, yGravity: Float) {
        try {
            model.update(xGravity, yGravity)
            events.value = GravityOk(model)
        } catch (error: Exception) {
            events.value = GravityError("Error updating gravity!")
        }
    }

    fun startRegisterListener() {
        sensorManager.registerListener(this, gravity, SensorManager.SENSOR_DELAY_FASTEST)
    }

    fun stopRegisterListener() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            updateGravity(event.values[0], event.values[1])
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}

sealed class GravityEvent
class GravityOk(val model: GravityModel) : GravityEvent()
class GravityError(val error: String) : GravityEvent()
