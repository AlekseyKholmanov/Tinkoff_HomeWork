package com.example.holmi_production.customview

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val customView = findViewById<MyCustomViewGroup>(R.id.custom_view_group)
        for(i in 0..16){
            val textView  = TextView(applicationContext)
            val text: String = if(i%3 == 0) "LONG TEST TEXT" else "short"
            textView.text = text
            customView.addView(textView)
        }
    }

}
