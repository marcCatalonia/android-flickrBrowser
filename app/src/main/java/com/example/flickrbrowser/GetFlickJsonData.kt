package com.example.flickrbrowser

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.Exception

class GetFlickJsonData (private val listener: OnDataAvailable) {

    private val TAG = "GetFlickJsonData"
    val scope = CoroutineScope(Dispatchers.IO)

    interface OnDataAvailable{
        fun onDataAvailable(data: List<Photo>)
        fun onError(exception: Exception)
    }

    fun getJsonData(json: String){
        Log.d(TAG, "getJsonData called")

        scope.launch {
            //try {
                val jsonData = JSONObject(json)
                val itemsArray = jsonData.getJSONArray("items")


                //Loop to read each array's object
                for(i in 0 until itemsArray.length()){
                    val jsonPhoto = itemsArray.getJSONObject(i)
                    val title = jsonPhoto.getString("title")
                    val author = jsonPhoto.getString("author")
                    val authorId = jsonPhoto.getString("author_Id")
                    val tags = jsonPhoto.getString("tags")

                    val jsonMedia = jsonPhoto.getJSONObject("media")
                    val photoUrl = jsonMedia.getString("m")
                    val link = photoUrl.replaceFirst("_m.jpg", "_b.jpg")

                }

            //}catch ()
        }
    }

}