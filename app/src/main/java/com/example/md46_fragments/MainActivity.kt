package com.example.md46_fragments

import android.app.Activity
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.md46_fragments.DataClasses.GalleryImage
import com.example.md46_fragments.databinding.ActivityMainBinding
import android.Manifest
import android.content.ContentUris
import android.opengl.Visibility
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity(), GalleryImageClickHandler {
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                turnPermissionWarning(false)
                // Разрешение получено - загружаем изображения
                loadImages()
            } else {
                turnPermissionWarning(true)
            }
        }

    private var listOfAllImages: MutableList<GalleryImage> = arrayListOf()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        turnPermissionWarning(false)
        initGallery()
        checkAndRequestPermission()
    }

    private fun checkAndRequestPermission() {
        val permission =
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                -> Manifest.permission.READ_MEDIA_IMAGES

                else -> Manifest.permission.READ_EXTERNAL_STORAGE
            }

        when {
            ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Разрешение уже есть - загружаем изображения
                loadImages()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this, permission
            ) -> {
                // Показываем объяснение перед запросом
                requestPermissionLauncher.launch(permission)
            }

            else -> {
                // Просто запрашиваем разрешение
                requestPermissionLauncher.launch(permission)
            }
        }
    }

    private fun turnPermissionWarning(mode: Boolean){
        binding.permissionDeniedTxt.isVisible = mode
    }

    private fun initGallery() {
        binding.rList.adapter = ImageRecyclerView(this, listOfAllImages)
        binding.rList.layoutManager = GridLayoutManager(this, 3)
    }

    private fun loadImages() {
        listOfAllImages.clear()
        getAllShownImagesPath()
        binding.rList.adapter?.notifyDataSetChanged()
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

        applicationContext.contentResolver.query(
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
                Log.d("GalleryImage", "ID: $id, Path: $path")
                listOfAllImages.add(GalleryImage(ContentUris.withAppendedId(collection, id), path))
            }
        }
    }

    override fun OnClick() {
        TODO("Not yet implemented")
    }

    override fun OnLongClick(): Boolean {
        TODO("Not yet implemented")
    }
}