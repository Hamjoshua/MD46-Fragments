package com.example.md46_fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.md46_fragments.DataClasses.GalleryImage
import com.example.md46_fragments.databinding.RViewBinding

class ImageRecyclerView(val clickHandler: GalleryImageClickHandler,
                        var data : MutableList<GalleryImage>) : RecyclerView.Adapter<ImageRecyclerView.ImageViewHolder>() {


    class ImageViewHolder(val binding : RViewBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RViewBinding.inflate(inflater, parent, false)

        return ImageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val galleryImageEntity = data[position]

        galleryImageEntity.setImage(holder.binding.rImage)

        holder.binding.rImage.setOnClickListener{
            clickHandler.onClick(galleryImageEntity)
        }

        holder.binding.rImage.setOnLongClickListener{
            clickHandler.onLongClick(galleryImageEntity.description, position)
        }
    }
}

interface GalleryImageClickHandler{
    abstract fun onClick(image: GalleryImage)

    abstract fun onLongClick(description: String, imageId: Int) : Boolean
}