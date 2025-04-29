package com.example.md46_fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.example.md46_fragments.DataClasses.GalleryImage
import com.example.md46_fragments.Fragments.ChangeDetailsFragment
import com.example.md46_fragments.Fragments.DetailsFullscreenFragment
import com.example.md46_fragments.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    GalleryImageClickHandler, ChangeDetailsFragment.DescriptionChangeListener {
    private val viewModel: GIViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        turnPermissionWarning(false)
        checkAndRequestPermission()
        initGallery()
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

    private fun turnPermissionWarning(mode: Boolean) {
        binding.permissionDeniedTxt.isVisible = mode
    }

    private fun initGallery() {
        binding.rList.adapter = ImageRecyclerView(this, viewModel.listOfAllImages.value!!.toMutableList())
        binding.rList.layoutManager = GridLayoutManager(this, 3)
    }

    private fun loadImages() {
        viewModel.loadImages(this)
        binding.rList.adapter?.notifyDataSetChanged()
    }

    override fun onClick(image: GalleryImage) {
        val fragment = DetailsFullscreenFragment().apply {
            arguments = Bundle().apply {
                putString("uri", image.link)
                putString("description", image.description)
            }
        }

        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onLongClick(description: String, imageId: Int): Boolean {
        val dialog = ChangeDetailsFragment().apply {
            arguments = Bundle().apply {
                putString("description", description)
                putInt("imageId", imageId)
            }
        }
        dialog.show(supportFragmentManager, "DETAILS")

        return true
    }

    override fun onChange(newDescription: String, imageId: Int) {
        viewModel.updateGalleryImage(newDescription, imageId)
        Toast.makeText(this, "Описание изменено!", Toast.LENGTH_SHORT).show()
    }
}