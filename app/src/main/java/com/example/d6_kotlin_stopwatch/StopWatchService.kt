package com.example.d6_kotlin_stopwatch

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.Timer
import java.util.TimerTask

class StopWatchService :Service() {
    override fun onBind(intent: Intent?): IBinder? = null
    private val timer = Timer();//java class timer

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.i("myTag", "service onStart")
        Log.i("myTag", "service getting intent extra")
        val time = intent.getDoubleExtra(CURRENT_TIME,0.0)//get the extra
        Log.i("myTag", "service timer.scheduleAtFixedRate")
        timer.scheduleAtFixedRate(StopWatchTimerTask(time),0,1000)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        Log.i("myTag", "service destroying timer canceled")
        timer.cancel()
        super.onDestroy()
    }

    companion object{
        const val CURRENT_TIME = "current time"
        const val UPDATED_TIME = "updated time"
    }

    private inner class StopWatchTimerTask(private var time:Double):TimerTask(){
        override fun run() {
            Log.i("myTag", "service running StopWatchTimerTask")
            val intent = Intent(UPDATED_TIME)
            time++
            Log.i("myTag", "service putting extra to Intent(UPDATED_TIME)")
            intent.putExtra(CURRENT_TIME,time)
            Log.i("myTag", "service sendBroadcast(intent) time: $time")
//            sendBroadcast(intent)
        }

    }
}