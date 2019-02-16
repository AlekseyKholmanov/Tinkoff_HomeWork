package com.example.holmi_production.homework_1

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.activity1_button)
        button.setOnClickListener {
            val intent = Intent(this,Activity2::class.java)
            startActivityForResult(intent,1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data==null)return
        val resultText:String = data.getStringExtra("result")
        val toast = Toast.makeText(this, resultText, Toast.LENGTH_LONG)
        toast.show()
    }
}
