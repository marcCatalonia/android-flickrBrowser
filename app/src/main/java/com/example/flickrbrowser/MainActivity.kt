 package com.example.flickrbrowser

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flickrbrowser.databinding.ActivityMainBinding
import com.example.flickrbrowser.databinding.ContentMainBinding
import java.lang.Exception
 import androidx.preference.PreferenceManager

 private const val TAG = "MainActivity"
class MainActivity : BaseActivity(), GetFlickJsonData.OnDataAvailable, RecyclerItemClickListener.OnRecyclerClickListener {
    private val flickrRecyclerViewAdapter = FlickrRecyclerViewAdapter(ArrayList<Photo>())

    //Classes to bind ids -> inspite of findViewById
    private lateinit var bindingActivityMainLayout: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate called")
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main) unnecessary because on line 38 we already call the same method


        bindingActivityMainLayout = ActivityMainBinding.inflate(layoutInflater)

        bindingActivityMainLayout.includedContentMainListView.recyclerView.layoutManager = LinearLayoutManager(this)
        bindingActivityMainLayout.includedContentMainListView.recyclerView.addOnItemTouchListener(RecyclerItemClickListener(this, bindingActivityMainLayout.includedContentMainListView.recyclerView, this))
        bindingActivityMainLayout.includedContentMainListView.recyclerView.adapter = flickrRecyclerViewAdapter
        setContentView(bindingActivityMainLayout.root)
        activateToolbar(false, bindingActivityMainLayout.toolbar)
        val url = createUri("https://www.flickr.com/services/feeds/photos_public.gne", "android,oreo", "en-us", true)

        //Instance to get the FLICKR Json
        val getRawData = GetRawData(this)

//        getRawData.setDownloadCompleteListener(this)
        getRawData.getJSON(url)

    }

    override fun onItemClick(view: View, position: Int) {
        Log.d(TAG, "onItemClick: started")
        Toast.makeText(this, "Normal tap at position $position", Toast.LENGTH_SHORT).show()
    }

    override fun onItemLongClick(view: View, position: Int) {
        Log.d(TAG, "onItemLongClick: starts")
        //Toast.makeText(this, "Long tap at position $position", Toast.LENGTH_SHORT).show()
        val photo = flickrRecyclerViewAdapter.getPhoto(position)
        if(photo != null){
            val intent = Intent(this, PhotoDetailsActivity::class.java)
            intent.putExtra(PHOTO_TRANSFER, photo)
            startActivity(intent)
        }
    }

    private fun createUri (baseURL: String, searchCriteria: String, lang: String, matchAll: Boolean): String{
        Log.d(TAG, "createUri starts")

        var uri = Uri.parse(baseURL)
        var builder = uri.buildUpon()
        builder = builder.appendQueryParameter("tags", searchCriteria)
        builder = builder.appendQueryParameter("tagmode", if(matchAll)"ALL" else "ANY")
        builder = builder.appendQueryParameter("lang", lang)
        builder = builder.appendQueryParameter("format", "json")
        builder = builder.appendQueryParameter("nojsoncallback", "1")
        uri = builder.build()

        return uri.toString()


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
            R.id.action_search -> {

                startActivity(Intent(this, SearchActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /*Function that returns the result from the Coroutines JSON in GetRawData*/
    fun onDownloadComplete(data: String, status: DownloadStatus){
        if(status == DownloadStatus.OK){
            Log.d(TAG, "onDownloadComplete called, data is $data")

            val getFlickJsonData = GetFlickJsonData(this)
            getFlickJsonData.getJsonData(data)
        }
        else{
            Log.d(TAG, "onDownloadComplete failed with status $status. Error message is $data")
        }
    }

    override fun onDataAvailable(data: List<Photo>) {

        Log.d(TAG, "onDataAvailable called")
        flickrRecyclerViewAdapter.loadNewData(data)
        Log.d(TAG, "onDataAvailable $data")
        Log.d(TAG, "onDataAvailable ends")
    }

    override fun onError(exception: Exception) {
        Log.d(TAG, "onError called with exception: ${exception.message}")
    }


    override fun onResume() {
        Log.d(TAG, "onResume starts")
        super.onResume()

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val queryResult = sharedPref.getString(FLICKR_QUERY, "")

        if(queryResult!!.isNotEmpty()){
            val url = createUri("https://www.flickr.com/services/feeds/photos_public.gne", queryResult, "en-us", true)

            //Instance to get the FLICKR Json
            val getRawData = GetRawData(this)

//        getRawData.setDownloadCompleteListener(this)
            getRawData.getJSON(url)
        }

        Log.d(TAG, ".onResume: ends")
    }
}