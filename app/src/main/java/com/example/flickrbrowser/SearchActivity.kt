package com.example.flickrbrowser

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView


class SearchActivity : BaseActivity() {
    private val TAG = "SearchActivity"
    private var searchView: SearchView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate starts")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(findViewById(R.id.toolbar))
        //activateToolbar(true)
        Log.d(TAG, "onCreate ends")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d(TAG, "onCreateOptionsMenu: starts")
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchManager= getSystemService(Context.SEARCH_SERVICE) as SearchManager //Get the class to handle the Search Manager
        searchView= menu.findItem(R.id.app_bar_search).actionView as SearchView //Get the reference to the appBar
        val searchableInfo = searchManager.getSearchableInfo(componentName)
        searchView?.setSearchableInfo(searchableInfo)

        Log.d(TAG, ".onCreateOptionsMenu $componentName")
        Log.d(TAG, ".onCreateOptionsMenu hint is ${searchView?.queryHint}")
        Log.d(TAG, ".onCreateOptionsMenu $searchableInfo")

        searchView?.isIconified = true
        return true
    }
}