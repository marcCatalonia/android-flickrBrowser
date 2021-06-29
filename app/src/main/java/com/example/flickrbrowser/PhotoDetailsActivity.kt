package com.example.flickrbrowser

import android.os.Bundle
import android.util.Log
import com.example.flickrbrowser.databinding.ActivityPhotoDetailsBinding
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
        //bindingPhotoDetailsLayoutActivity.includedContentPhotoDetails.photoTitle.text = "Title: "+ photo.title
        //bindingPhotoDetailsLayoutActivity.includedContentPhotoDetails.photoTags.text = "Tags "+photo.tags
        //bindingPhotoDetailsLayoutActivity.includedContentPhotoDetails.photoAuthor.text = "Author: "+photo.author

        //Show the text gaps from the resource file String.xml calling resources.GetString and its tag name
        bindingPhotoDetailsLayoutActivity.includedContentPhotoDetails.photoTitle.text = resources.getString(R.string.photo_title_text, photo.title)
        bindingPhotoDetailsLayoutActivity.includedContentPhotoDetails.photoTags.text = resources.getString(R.string.photo_tags_text, photo.tags)
        //bindingPhotoDetailsLayoutActivity.includedContentPhotoDetails.photoAuthor.text = resources.getString(R.string.photo_author_text, "my", "red", "car")


        //Library to download the image and put it on the drawable
        Picasso.get().load(photo.link).error(R.drawable.placeholder).placeholder(R.drawable.placeholder).into(bindingPhotoDetailsLayoutActivity.includedContentPhotoDetails.photoImage)

    }
}