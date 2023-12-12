package dev.danilobarreto.cronometroiasd

import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var chronometer: Chronometer
    private lateinit var startStopButton: Button
    private lateinit var resetButton: Button

    private var isRunning = false
    private var elapsedTime: Long = 0

    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chronometer = findViewById(R.id.chronometer)
        startStopButton = findViewById(R.id.startStopButton)
        resetButton = findViewById(R.id.resetButton)

        startStopButton.setOnClickListener {
            if (isRunning) {
                stopChronometer()
            } else {
                startChronometer()
            }
        }

        resetButton.setOnClickListener {
            resetChronometer()
        }
    }

    private fun startChronometer() {
        chronometer.base = SystemClock.elapsedRealtime() - elapsedTime
        chronometer.start()
        startStopButton.text = getString(R.string.stop)
        isRunning = true

        handler.postDelayed(timerRunnable, 100)
    }

    private fun stopChronometer() {
        chronometer.stop()
        startStopButton.text = getString(R.string.start)
        isRunning = false

        handler.removeCallbacks(timerRunnable)
    }

    private fun resetChronometer() {
        chronometer.base = SystemClock.elapsedRealtime()
        elapsedTime = 0
        if (!isRunning) {
            chronometer.stop()
        }
        startStopButton.text = getString(R.string.start)
        isRunning = false
    }

    private val timerRunnable = object : Runnable {
        override fun run() {
            val currentTime = SystemClock.elapsedRealtime() - chronometer.base
            elapsedTime = currentTime
            handler.postDelayed(this, 100)
        }
    }
}
