package com.example.md46_fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.md46_fragments.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), GalleryImageClickHandler {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initGallery()
    }

    fun initGallery(){
        binding.rList.adapter = ImageRecyclerView(this)
        binding.rList.layoutManager = GridLayoutManager(this, 3)
    }

    override fun OnClick() {
        TODO("Not yet implemented")
    }

    override fun OnLongClick(): Boolean {
        TODO("Not yet implemented")
    }
}