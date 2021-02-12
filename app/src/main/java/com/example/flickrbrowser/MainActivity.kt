package com.example.flickrbrowser

import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        //Instance to get the FLICKR Json
        val getRawData = GetRawData()
        getRawData.setDownloadCompleteListener(this)
        getRawData.getJSON("https://www.flickr.com/services/feeds/photos_public.gne?tags=android,oreo&format=json&nojsoncallback=1&lang=es-us")

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d(TAG, "onCreateOptionsMenu called")
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onCreateOptionsItemSelected called")
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    /*Function that returns the result from the Coroutines JSON in GetRawData*/
    fun onDownloadComplete(data: String, status: DownloadStatus){
        if(status == DownloadStatus.OK){
            Log.d(TAG, "onDownloadComplete called, data is $data")
        }
        else{
            Log.d(TAG, "onDownloadComplete failed with status $status. Error message is $data")
        }
    }
}