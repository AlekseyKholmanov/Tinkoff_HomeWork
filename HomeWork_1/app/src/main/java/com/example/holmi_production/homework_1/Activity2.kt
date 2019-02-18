package com.example.holmi_production.homework_1

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button

class Activity2: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2)
        getPermission()
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, IntentFilter("testApp"))
        val createServiceButton = findViewById<Button>(R.id.activity2_button)
        createServiceButton.setOnClickListener{
            val intentService = Intent(this, MyService::class.java)
            Log.d("testApp","try to start")
            startService(intentService)
        }

    }

    private var mMessageReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            val message = intent?.getStringExtra("KEY")
            Log.d("testApp", "Got message: $message")
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

    private fun getPermission(){
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CONTACTS), 2)
            }
    }
}