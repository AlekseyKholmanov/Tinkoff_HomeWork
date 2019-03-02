package com.example.holmi_production.customview

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.marginBottom
import com.google.android.material.chip.Chip

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val customViewUp = findViewById<MyCustomViewGroup>(R.id.custom_view_group_up)
        val customViewDown = findViewById<MyCustomViewGroup>(R.id.custom_view_group_down)
        for(i in 0..16){
            val textView  = TextView(this)
            val text: String = if(i%3 == 0) "LONG TEST TEXT" else "short"
            textView.text = text
            customViewUp.addView(textView)
        }
        for (i in 0..5){
            val chip = Chip(this)
            chip.text = "textoteron implem"
            chip.chipIcon = ContextCompat.getDrawable(this, R.color.colorPrimary)
            chip.isCloseIconEnabled = true
            customViewDown.addView(chip)
        }

    }

}
