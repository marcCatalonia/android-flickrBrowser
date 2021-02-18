package com.example.flickrbrowser

import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.flickrbrowser.databinding.ContentMainBinding
import com.example.flickrbrowser.databinding.ContentPhotoDetailsBinding
import com.squareup.picasso.Picasso

private const val TAG = "PhotoDetailsActivity"
class PhotoDetailsActivity : BaseActivity() {
    private lateinit var bindingPhotoDetailsLayout: ContentPhotoDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_details)
        Log.d(TAG, "onCreate called")

        activateToolbar(true)

        bindingPhotoDetailsLayout = ContentPhotoDetailsBinding.inflate(layoutInflater)
        setContentView(bindingPhotoDetailsLayout.root)

        val photo = intent.getSerializableExtra(PHOTO_TRANSFER) as Photo
        Log.d(TAG, "photo --> $photo")

        bindingPhotoDetailsLayout.photoTitle.text = photo.title
        bindingPhotoDetailsLayout.photoTags.text = photo.tags
        bindingPhotoDetailsLayout.photoAuthor.text = photo.author

        //Library to download the image and put it on the drawable
        Picasso.get().load(photo.link).error(R.drawable.placeholder).placeholder(R.drawable.placeholder).into(bindingPhotoDetailsLayout.photoImage)

    }
}