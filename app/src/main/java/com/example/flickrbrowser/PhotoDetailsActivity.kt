package com.example.flickrbrowser

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import com.example.flickrbrowser.databinding.ActivityPhotoDetailsBinding
import com.example.flickrbrowser.databinding.ContentPhotoDetailsBinding
import com.squareup.picasso.Picasso

private const val TAG = "PhotoDetailsActivity"
class PhotoDetailsActivity : BaseActivity() {
    private lateinit var bindingPhotoDetailsLayoutActivity : ActivityPhotoDetailsBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*Create an instance of the xml layout we want to bind:
        * "activity_photo_details.xml" -> "ActivityPhotoDetailsBinding"
        * */
        bindingPhotoDetailsLayoutActivity = ActivityPhotoDetailsBinding.inflate(layoutInflater)

        /*Call the toolbar through the method we created in the BaseActivity and pass the toolbar View*/
        activateToolbar(true, bindingPhotoDetailsLayoutActivity.toolbar)
        setContentView(bindingPhotoDetailsLayoutActivity.root)

        Log.d(TAG, "onCreate called")

        val photo = intent.getSerializableExtra(PHOTO_TRANSFER) as Photo
        Log.d(TAG, "photo --> $photo")

        /*We access to the activity views through its ID*/
        bindingPhotoDetailsLayoutActivity.includedContentPhotoDetails.photoTitle.text = photo.title
        bindingPhotoDetailsLayoutActivity.includedContentPhotoDetails.photoTags.text = photo.tags
        bindingPhotoDetailsLayoutActivity.includedContentPhotoDetails.photoAuthor.text = photo.author


        //Library to download the image and put it on the drawable
        Picasso.get().load(photo.link).error(R.drawable.placeholder).placeholder(R.drawable.placeholder).into(bindingPhotoDetailsLayoutActivity.includedContentPhotoDetails.photoImage)

    }
}