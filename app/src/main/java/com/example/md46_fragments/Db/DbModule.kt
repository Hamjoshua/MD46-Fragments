package com.example.md46_fragments.Db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.md46_fragments.DataClasses.GalleryImage
import com.example.md46_fragments.DataClasses.GalleryImageDAO

@Database(entities = [GalleryImage::class], version = 2, exportSchema = false)
abstract class DbModule : RoomDatabase() {
    abstract fun giDao() : GalleryImageDAO
}