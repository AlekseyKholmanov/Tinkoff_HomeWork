package com.example.holmi_production.homework_1

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button

class Activity2: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2)
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, IntentFilter("testApp"))
        val createServiceButton = findViewById<Button>(R.id.activity2_button)
        createServiceButton.setOnClickListener{
            val intentService = Intent(this, MyService::class.java)
            startService(intentService)
            Log.d("testApp","try to start")
        }

    }
    private var mMessageReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            val message = intent?.getStringExtra("KEY")
            Log.d("testApp","Got messsage " + message)
            val intent = Intent()
            intent.putExtra("result",message)
            setResult(Activity.RESULT_OK,intent)
            finish()
        }

    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
        super.onDestroy()
    }

}