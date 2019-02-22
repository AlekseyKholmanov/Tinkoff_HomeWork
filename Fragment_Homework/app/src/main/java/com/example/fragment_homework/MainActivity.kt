package com.example.fragment_homework


import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    private var count: Int = 1
    private var delButton: Button? = null
    private var optionsMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        delButton = findViewById(R.id.action_delete2)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        optionsMenu = menu
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu!!.getItem(1).setEnabled(false)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val fragment = TestFragment.newInstance(count)
        if (item.itemId == R.id.action_add1){
            supportFragmentManager.beginTransaction()
                .add(R.id.container, fragment)
                .commit()
            count++
            Log.d("qwerty","add")
        }
        if(item.itemId == R.id.action_delete2)
        {
            supportFragmentManager.beginTransaction()
                .remove(fragment)
                .commit()
            count--
            Log.d("qwerty", "delete")
        }
        updateButtonState()
        return super.onOptionsItemSelected(item)
    }

    private fun updateButtonState(){
        val button = optionsMenu!!.findItem(R.id.action_delete2)
        if(count<1)
            return
        if (count==1){
            button?.setEnabled(false)
        }
        else{
            button?.setEnabled(true)
        }
    }
}
