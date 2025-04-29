package com.example.md46_fragments.DataClasses

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface GalleryImageDAO {
    @Query("Select * from GalleryImages")
    fun getAllGalleryImages() : List<GalleryImage>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGalleryImage(galleryImage: GalleryImage)

    @Query("Update GalleryImages Set description = :description where id = :id")
    fun updateDescription(description: String, id: Int)

    @Update
    fun updateGalleryImage(newGalleryImage: GalleryImage)
}