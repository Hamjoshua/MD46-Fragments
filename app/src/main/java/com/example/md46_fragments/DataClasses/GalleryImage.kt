package com.example.md46_fragments.DataClasses

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide

data class GalleryImage(
    val link : String,
    val description: String
){
    fun setImage(imageView: ImageView){
        Glide.with(imageView.context)
            .load(Uri.parse(link))
            .into(imageView)
    }

    fun getImage(){
        // TODO
    }
}
