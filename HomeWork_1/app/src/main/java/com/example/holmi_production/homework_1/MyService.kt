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
        val respIntent = Intent("testApp")
        respIntent.putExtra("KEY","MESSAGE FOR FIRST ACTIVITY")
        getPermission()
        getContacs()
        LocalBroadcastManager.getInstance(this).sendBroadcast(respIntent)
        Log.d("testApp","responce sended")
    }

    private fun getContacs(){
        val cr = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null)
        do
        {
            val name:String = cr.getString(
                cr.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
            Log.d("tesApp", name)
        }
            while (cr.moveToNext())
        cr.close()
        Log.d("testApp", "count contacts: " + cr.count)
    }

    private fun getPermission(){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(Activity(), arrayOf(android.Manifest.permission.READ_CONTACTS), 2)
        }
    }
}