package com.example.vgestas.sensor_pong.Model

class GravityModel(private var xGravityForce: Float = 0.0F, private var yGravityForce: Float = 0.0F, private var gravityForce: Float = 11.00F) {

    // Calculate the ratio with respect to gravity
    var xAxisTranslation: Float = 0.0F
        get() {
            return xGravityForce / gravityForce
        }

    var yAxisTranslation: Float = 0.0F
        get() {
            return yGravityForce / gravityForce
        }

    // Update the ball position
    fun update(xGravity: Float, yGravity: Float) {
        xGravityForce = xGravity
        yGravityForce = yGravity
    }
}