package com.lucky.animeku.utils

import android.app.Application
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast

class CheckInternetConnection {
    private var timer = 0
    private var handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    private var application: Application

    constructor(application: Application) {
        this.application = application
    }

    fun startCheckConsumeTime() {
        runnable = Runnable {
            timer++
            Log.d("Timer", timer.toString())
            if (timer % 60 == 0) {
                Toast.makeText(application.applicationContext, "Suka aplikasi ini? Beritahukan teman anda", Toast.LENGTH_SHORT).show()
            }

            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }

    fun stopCheckConsumeTime() {
        handler.removeCallbacks(runnable)
    }
}