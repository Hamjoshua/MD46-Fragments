package com.example.md46_fragments

import android.app.Activity
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.md46_fragments.DataClasses.GalleryImage
import com.example.md46_fragments.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), GalleryImageClickHandler {
    companion object {
        private const val PERMISSION_REQUEST_CODE = 123
    }
    private var listOfAllImages : MutableList<GalleryImage> = arrayListOf()

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        requestPermission()
        getAllShownImagesPath(this)
        initGallery()
    }



    private fun initGallery() {
        binding.rList.adapter = ImageRecyclerView(this, listOfAllImages)
        binding.rList.layoutManager = GridLayoutManager(this, 3)
    }

    private fun getAllShownImagesPath(activity: Activity) {
        val uri: Uri
        val cursor: Cursor?
        val column_index_data: Int
        val column_index_folder_name: Int

        var absolutePathOfImage: String? = null
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.Media._ID,
            MediaStore.Images.Media.MIME_TYPE,
            MediaStore.Images.Media.DATA)
        cursor = activity.contentResolver.query(
            uri, projection, null,
            null, null
        )
        column_index_data = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data)
            listOfAllImages.add(
                GalleryImage(
                    absolutePathOfImage,
                    ""
                )
            )
        }
    }

    private fun requestPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED -> {
                ActivityCompat.requestPermissions(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == PERMISSION_REQUEST_CODE){

        }
    }

    override fun OnClick() {
        TODO("Not yet implemented")
    }

    override fun OnLongClick(): Boolean {
        TODO("Not yet implemented")
    }
}