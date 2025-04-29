package com.example.md46_fragments.Db

import androidx.lifecycle.LiveData
import com.example.md46_fragments.DataClasses.GalleryImage
import com.example.md46_fragments.DataClasses.GalleryImageDAO
import javax.inject.Inject

class GalleryImageRepo @Inject constructor(
    private val giDao: GalleryImageDAO
) {

    fun getAllGi() : List<GalleryImage> {
        return giDao.getAllGalleryImages()
    }

    fun insertGi(gi: GalleryImage) {
        giDao.insertGalleryImage(gi)
    }

    fun updateDescriptionOfGi(description: String, id: Int) {
        giDao.updateDescription(description, id)
    }
}