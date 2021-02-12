package com.example.flickrbrowser

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL

private const val TAG = "GetRawData"
enum class DownloadStatus{
    OK, NOT_INITIALIZED, FAILED_OR_EMPTY, PERMISSIONS_ERROR, ERROR
}

class GetRawData {
    val scope = CoroutineScope(Dispatchers.IO)
    var downloadStatus = DownloadStatus.OK

    fun getJSON(name: String){
        Log.d(TAG, "GetJson called")
        scope.launch {


            try{
                val json = URL(name).readText()


                withContext(Dispatchers.Main){
                    Log.d(TAG, "[getJson()] : $json")
                }

            }catch (e: Exception){
                var errorMessage = when(e){
                    is MalformedURLException -> {
                        downloadStatus =  DownloadStatus.NOT_INITIALIZED
                        "do in Background: Invalid URL ${e.message}"
                    }
                    is SecurityException ->{
                        downloadStatus = DownloadStatus.PERMISSIONS_ERROR
                        "doInBackground: Security exception: Needs permission? ${e.message}"
                    }
                    else ->{
                        downloadStatus = DownloadStatus.ERROR
                        "Unknown error: ${e.message}"
                    }
                }

                Log.d(TAG, errorMessage)


            }


        }
    }
}