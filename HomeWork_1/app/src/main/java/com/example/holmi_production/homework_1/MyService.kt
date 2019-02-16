package com.example.holmi_production.homework_1

import android.app.IntentService
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import android.util.Log

class MyService: IntentService("MyService") {

    override fun onCreate() {
        super.onCreate()
        Log.d("TestApp","IService created")
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d("TestApp", "Intent handle")
        val respIntent = Intent("testApp")
        respIntent.putExtra("KEY","MESSAGE FOR FIRST ACTIVITY")
        LocalBroadcastManager.getInstance(this).sendBroadcast(respIntent)
        Log.d("testApp","responce sended")
    }
}