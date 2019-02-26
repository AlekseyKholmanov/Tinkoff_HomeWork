package com.example.holmi_production.customview

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
        for(i in 0..10){
            textView.setText("$i сколько символов?")
            customView.addView(textView)
        }
    }
}
