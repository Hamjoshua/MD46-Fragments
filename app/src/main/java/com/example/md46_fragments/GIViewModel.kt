package com.example.md46_fragments

import android.content.ContentUris
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.md46_fragments.DataClasses.GalleryImage
import com.example.md46_fragments.Db.GalleryImageRepo
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GIViewModel @Inject constructor(
    private val giRepository: GalleryImageRepo
) : ViewModel() {
    private var _listOfAllImages: MutableLiveData<List<GalleryImage>> =
        MutableLiveData<List<GalleryImage>>()

    val listOfAllImages: List<GalleryImage> = _listOfAllImages.value!!

    fun loadImages(context: Context) {
        _listOfAllImages.value = giRepository.getAllGi()

        if (_listOfAllImages.value!!.isEmpty()) {
            getAllShownImagesPath(context)
            _listOfAllImages.value = giRepository.getAllGi()
        }
    }

    private fun getAllShownImagesPath(context: Context) {
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