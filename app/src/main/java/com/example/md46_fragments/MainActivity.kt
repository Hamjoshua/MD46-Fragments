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
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.md46_fragments.DataClasses.GalleryImage
import com.example.md46_fragments.Fragments.ChangeDetailsFragment
import com.example.md46_fragments.Fragments.DetailsFullscreenFragment
import com.example.md46_fragments.Fragments.GalleryFragment
import com.example.md46_fragments.Fragments.GalleryFragmentDirections
import com.example.md46_fragments.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity()
     {
    private lateinit var binding: ActivityMainBinding
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                turnPermissionWarning(false)
                // Разрешение получено - загружаем изображения

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




}