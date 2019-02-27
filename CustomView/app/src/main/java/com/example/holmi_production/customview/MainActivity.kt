package com.example.holmi_production.customview

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val customView = findViewById<MyCustomViewGroup>(R.id.custom_flight_view)
        var textView  = TextView(applicationContext)
        textView.textSize = 24f
        for(i in 0..9){
            val textView  = TextView(applicationContext)
            textView.text = "$i мволов?"
            customView.addView(textView)
        }
    }
}
