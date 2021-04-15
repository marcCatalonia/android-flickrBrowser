package com.example.flickrbrowser

import android.util.Log
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

internal const val FLICKR_QUERY = "FLICKR_QUERY"
internal const val  PHOTO_TRANSFER = "PHOTO_TRANSFER"
open class BaseActivity : AppCompatActivity( ) {
    private val TAG = "BaseActivity"


    internal fun activateToolbar(enableHome: Boolean, toolbar: Toolbar){
        Log.d(TAG, "activateToolbar")

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(enableHome)
        Log.d(TAG, "supportActionBar is null? ${supportActionBar?.title}")
    }
}