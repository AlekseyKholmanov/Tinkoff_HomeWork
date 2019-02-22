package com.example.fragment_homework


import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {

    private var count: Int = 1
    private var optionsMenu: Menu? = null
    private var SAVE_KEY = "key_number"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putInt(SAVE_KEY,count)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        if(savedInstanceState!=null){
            count = savedInstanceState.getInt(SAVE_KEY)
        }
        super.onRestoreInstanceState(savedInstanceState)
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
        val manager = supportFragmentManager
        val fragment = TestFragment.newInstance(count)
        if (item.itemId == R.id.action_add){
            manager.beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack(count.toString())
                .commit()
            count++
            Log.d("qwerty","add")
        }
        if(item.itemId == R.id.action_delete)
        {
            onBackPressed()
            Log.d("qwerty", "delete")
        }
        updateButtonState()
        return super.onOptionsItemSelected(item)
    }

    private fun updateButtonState(){
        val button = optionsMenu!!.findItem(R.id.action_delete)
        if(count<1)
            return
        if (count==1){
            button?.setEnabled(false)
        }
        else{
            button?.setEnabled(true)
        }
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount>0) {
            supportFragmentManager.popBackStack()
            --count
            updateButtonState()
            Log.d("qwerty", "back pressed")
        }
        if (supportFragmentManager.backStackEntryCount == 0)
        finish()
    }
}
