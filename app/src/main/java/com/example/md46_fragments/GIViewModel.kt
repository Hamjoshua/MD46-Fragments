package com.example.md46_fragments

import android.content.ContentUris
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.md46_fragments.DataClasses.GalleryImage
import com.example.md46_fragments.Db.GalleryImageRepo
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class GIViewModel @Inject constructor(
    private val giRepository: GalleryImageRepo,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private var _listOfAllImages: MutableLiveData<List<GalleryImage>> =
        MutableLiveData<List<GalleryImage>>()

    val listOfAllImages: LiveData<List<GalleryImage>> = _listOfAllImages

    fun loadImages(context: Context) {
        _listOfAllImages.value = giRepository.getAllGi()

        if (_listOfAllImages.value!!.isEmpty()) {
            Log.d("ViewModel", "DB is empty")
            getAllShownImagesPath()
            _listOfAllImages.value = giRepository.getAllGi()
        }
        else{
            Log.d("ViewModel", "DB is not empty, list has values")
        }
    }

    fun updateGalleryImage(newDescription: String, imageId: Int){
        val galleryImage = _listOfAllImages.value!![imageId]
        galleryImage.description = newDescription
        giRepository.insertGi(galleryImage)
    }

    private fun getAllShownImagesPath() {
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.MIME_TYPE,
            MediaStore.Images.Media.DATA
        )
        val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        getApplication(context).contentResolver.query(
            collection,
            projection,
            null,
            null,
            sortOrder
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val path = cursor.getString(dataColumn)
                val gi = GalleryImage(0, path, path)

                giRepository.insertGi(gi)
            }
        }
    }
}