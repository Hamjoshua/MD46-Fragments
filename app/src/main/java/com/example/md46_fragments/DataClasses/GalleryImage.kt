package com.example.md46_fragments.DataClasses

import android.net.Uri
import android.widget.ImageView
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bumptech.glide.Glide

@Entity(tableName = "GalleryImages")
data class GalleryImage(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val link : String,
    var description: String
){
    fun setImage(imageView: ImageView){
        Glide.with(imageView.context)
            .load(link)
            .into(imageView)
    }
}
