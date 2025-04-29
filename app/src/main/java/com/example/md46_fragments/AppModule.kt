package com.example.md46_fragments

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.md46_fragments.DataClasses.GalleryImageDAO
import com.example.md46_fragments.Db.GalleryImageDatabase
import com.example.md46_fragments.Db.GalleryImageRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideUserDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app, GalleryImageDatabase::class.java, "GalleryImageDatabase"
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideGalleryImageDao(db: GalleryImageDatabase) = db.giDao()

    @Provides
    @Singleton
    fun provideGalleryImageRepository(giDao: GalleryImageDAO): GalleryImageRepo {
        return GalleryImageRepo(giDao)
    }
}

@HiltAndroidApp
class AppModule : Application() {

}