package com.example.flickrbrowser

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class FlickrImageViewHolder(view: View): RecyclerView.ViewHolder(view){
    var thumbnail: ImageView = view.findViewById(R.id.thumbnail)
    var title : TextView = view.findViewById(R.id.title)
}

class FlickrRecyclerViewAdapter (private var photoList: List<Photo>): RecyclerView.Adapter<FlickrImageViewHolder>() {
    private val TAG = "FlickrRecyclerViewAdapt"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrImageViewHolder {
        //Called by the layout manager when it needs a new view
        Log.d(TAG, "onCreateViewHolder new view called")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.browse, parent, false)
        return FlickrImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlickrImageViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount called")
        return if(photoList.isNotEmpty()) photoList.size else 0
    }

    fun loadNewData(newPhotos: List<Photo>){
        photoList = newPhotos
        notifyDataSetChanged()
    }
    
    fun getPhoto(position: Int): Photo?{
        return if(photoList.isNotEmpty()) photoList[position] else null
    }
}