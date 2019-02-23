package com.example.fragment_homework


import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var optionsMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        optionsMenu = menu
        return true
    }

    override fun onResume() {
        super.onResume()
        supportFragmentManager.addOnBackStackChangedListener {
            updateStack()
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if(supportFragmentManager.backStackEntryCount == 0)
            menu!!.findItem(R.id.action_delete).isEnabled = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val manager = supportFragmentManager
        val count = manager.backStackEntryCount
        if (item.itemId == R.id.action_add){
            val fragment = TestFragment.newInstance(count+1)
            if(count==0){
            manager.beginTransaction()
                .add(R.id.container, fragment, count.toString())
                .addToBackStack(count.toString())
                .commit()
                }
            else{
                val fr = manager.findFragmentByTag((count-1).toString())
                manager.beginTransaction()
                    .replace(fr?.id!!, fragment, count.toString())
                    .addToBackStack(count.toString())
                    .commit()
            }
            Log.d("qwerty","add" + count.toString())
        }
        if(item.itemId == R.id.action_delete)
        {
            manager.beginTransaction()
                .remove(manager.findFragmentByTag((count-1).toString())!!)
                .commit()
            manager.popBackStack()
            Log.d("qwerty", "delete" + count.toString())
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateStack() {
        Log.d("qwerty","stack was changed")
        val manager = supportFragmentManager
        for (entry in 0 until manager.backStackEntryCount) {
            Log.i("qwerty", "Found fragment: " + manager.getBackStackEntryAt(entry).name)
        }
        Log.d("qwerty", "currentVariable count :" + manager.backStackEntryCount.toString())
        updateButtonState()
    }

    private fun updateButtonState(){
        val button = optionsMenu!!.findItem(R.id.action_delete)
        button?.isEnabled = supportFragmentManager.backStackEntryCount>0
    }
}
