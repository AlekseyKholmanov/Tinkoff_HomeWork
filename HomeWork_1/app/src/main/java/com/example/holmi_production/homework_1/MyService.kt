package com.example.holmi_production.homework_1

import android.app.Activity
import android.app.IntentService
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.ContactsContract
import android.support.v4.app.ActivityCompat.requestPermissions
import android.support.v4.content.ContextCompat
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import java.util.jar.Manifest

class MyService: IntentService("MyService") {

    override fun onCreate() {
        super.onCreate()
        Log.d("TestApp","IService created")
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d("TestApp", "Intent handle")
        val contactsName:String = getContacs()
        val respIntent = Intent("testApp")
        respIntent.putExtra("KEY",contactsName)
        LocalBroadcastManager.getInstance(this).sendBroadcast(respIntent)
        Log.d("testApp","responce sended")
    }

    private fun getContacs(): String {
        val cr = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null)
        val builder = StringBuilder()
        if(cr.count >= 1) {
            while (cr.moveToNext()) {
                val name: String = cr.getString(
                    cr.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                builder.append("$name ")
                Log.d("testApp", name)
            }
            cr.close()
        }
        else {
            builder.append("contacts not found")
        }
        Log.d("testApp","cursor closed")
        return builder.toString()
    }
}