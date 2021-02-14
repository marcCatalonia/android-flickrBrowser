package com.example.flickrbrowser

import android.util.Log
import kotlinx.coroutines.*
import org.json.JSONException
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
            try {
                val jsonData = JSONObject(json)
                val itemsArray = jsonData.getJSONArray("items")
                val photoList = ArrayList<Photo>()

                //Loop to read each array's object
                for(i in 0 until itemsArray.length()){
                    val jsonPhoto = itemsArray.getJSONObject(i)
                    val title = jsonPhoto.getString("title")
                    val author = jsonPhoto.getString("author")
                    val authorId = jsonPhoto.getString("author_id")
                    val tags = jsonPhoto.getString("tags")

                    val jsonMedia = jsonPhoto.getJSONObject("media")
                    val photoUrl = jsonMedia.getString("m")
                    val link = photoUrl.replaceFirst("_m.jpg", "_b.jpg")

                    val photoObject = Photo(title, author, authorId, link, tags, photoUrl)
                    photoList.add(photoObject)
                    Log.d(TAG, ".doInBackground $photoObject")

                }


                //Return the PhotoList
                withContext(Dispatchers.Main){
                    Log.d(TAG, "WithContext starts")
                    listener.onDataAvailable(photoList)
                    Log.d(TAG, "WithContext ends")
                }

            }catch (e: JSONException){
                e.printStackTrace()
                scope.cancel()
                Log.e(TAG, "doInBackground: Error processing Json ${e.message}")
                listener.onError(e)
            }

            Log.d(TAG, "doInBackground ends")

        }


    }

}