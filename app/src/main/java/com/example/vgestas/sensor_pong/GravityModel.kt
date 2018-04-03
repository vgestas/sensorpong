package com.example.vgestas.sensor_pong

class GravityModel(private var xGravityForce: Float = 0.0F, private var yGravityForce: Float = 0.0F, private var gravityForce: Float = 9.82F) {
    var xAxisTranslation: Float = 0.0F
        get() {
            return xGravityForce / gravityForce
        }

    var yAxisTranslation: Float = 0.0F
        get() {
            return yGravityForce / gravityForce
        }

    fun update(xGravity: Float, yGravity: Float) {
        xGravityForce = xGravity
        yGravityForce = yGravity
    }
}