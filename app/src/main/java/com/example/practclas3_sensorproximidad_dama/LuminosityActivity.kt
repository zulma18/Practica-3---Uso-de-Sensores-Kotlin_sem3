package com.example.practclas3_sensorproximidad_dama

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

class LuminosityActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null
    private var lightValue: Float = 0f
    private lateinit var btnMostrar: Button
    private lateinit var btnEnviar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_luminosity)

        btnMostrar = findViewById(R.id.btnMostrar)
        btnEnviar = findViewById(R.id.btnEnviar)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        if (lightSensor == null) {
            Toast.makeText(this, "Sensor de luz no disponible", Toast.LENGTH_SHORT).show()
        }

        // Al presionar el botón se actualizará el texto con el valor actual del sensor
        btnMostrar.setOnClickListener {
            btnMostrar.text = "Intensidad: $lightValue LUX"
        }

        btnEnviar.setOnClickListener {
            // Número de ejemplo en formato internacional (modifica este valor según sea necesario)
            val numero = "+50312345678"
            if (lightValue > 0) {
                enviarWhatsApp("Intensidad de luz: $lightValue lux", numero)
            } else {
                Toast.makeText(this, "Aún no se ha detectado una medición", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        lightSensor?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
            lightValue = event.values[0]
            // Se actualiza automáticamente el texto del botón con el valor actual
            btnMostrar.text = "Intensidad: $lightValue LUX"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun enviarWhatsApp(mensaje: String, numero: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://api.whatsapp.com/send?phone=$numero&text=$mensaje")
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "No se pudo abrir WhatsApp", Toast.LENGTH_SHORT).show()
        }
    }
}
