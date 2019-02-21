package com.example.fragment_homework


import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    private var count: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.action_add){
            supportFragmentManager.beginTransaction()
                .add(R.id.container, TestFragment.newInstance(count))
                .commit()
            count++
            Log.d("qwerty","add")
        }
        if(item.itemId == R.id.action_delete)
        {
            supportFragmentManager.beginTransaction()
                .remove(TestFragment())
                .commit()
            count--
            Log.d("qwerty", "delete")
            //TODO
        }
        return super.onOptionsItemSelected(item)
    }
}
