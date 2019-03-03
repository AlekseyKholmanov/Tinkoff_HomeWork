package com.example.holmi_production.customview

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.chip.Chip

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val customViewUp = findViewById<MyCustomViewGroup>(R.id.custom_view_group_up)
        val customViewDown = findViewById<MyCustomViewGroup>(R.id.custom_view_group_down)

        for(i in 0..16){
            val chip = Chip(this)
            chip.setOnCloseIconClickListener { v ->
                run {

                    val parent = v.parent as ViewGroup
                    val id = parent.id
                    parent.removeView(v)
                    if(id == R.id.custom_view_group_up)
                        customViewDown.addView(v)
                    else
                        customViewUp.addView(v)
                    Log.d("qwerty", "clicked")
                }
            }
            chip.chipIcon = ContextCompat.getDrawable(this, R.color.colorPrimary)
            chip.isCloseIconEnabled = true
            val text: String = if(i%3 == 0) "$i LONG TEST TEXT" else "$i short"
            chip.text = text
            if (i>8)
                customViewUp.addView(chip)
            else
                customViewDown.addView(chip)
        }

    }

}
