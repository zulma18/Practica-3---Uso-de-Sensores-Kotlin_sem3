package com.example.practclas3_sensorproximidad_dama

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout

class ProximityActivity : AppCompatActivity() {
    private var proximitySensor: Sensor? = null
    private lateinit var sensorManager: SensorManager
    private lateinit var sensorEventListener: SensorEventListener
    private lateinit var layout: ConstraintLayout
    private lateinit var proximityButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proximity)

        // Referencias a los elementos de la UI
        layout = findViewById(R.id.proximityLayout)
        proximityButton = findViewById(R.id.proximityButton)

        // Configuración del SensorManager y del sensor de proximidad
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        // Verificamos si el sensor de proximidad está disponible
        if (proximitySensor == null) {
            proximityButton.text = "Sensor de proximidad no disponible"
        } else {
            // Configuración del listener para recibir eventos del sensor
            sensorEventListener = object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent) {
                    val proximityValue = event.values[0]
                    val maxRange = proximitySensor?.maximumRange ?: 0f

                    if (proximityValue < maxRange) {
                        // Si estamos cerca del sensor
                        layout.setBackgroundColor(Color.rgb(98, 208, 225))
                        proximityButton.setBackgroundColor(Color.rgb(234, 26, 134))
                        proximityButton.setTextColor(Color.WHITE)
                        proximityButton.text = "Cerca! Valor: $proximityValue"
                    } else {
                        // Si estamos lejos del sensor
                        layout.setBackgroundColor(Color.LTGRAY)
                        proximityButton.setBackgroundColor(Color.TRANSPARENT)
                        proximityButton.setTextColor(Color.WHITE)
                        proximityButton.text = "Lejos! Valor: $proximityValue"
                    }
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Registra el listener para el sensor de proximidad
        proximitySensor?.let {
            sensorManager.registerListener(sensorEventListener, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        // Desregistra el listener cuando la actividad se pausa
        sensorManager.unregisterListener(sensorEventListener)
    }
}
