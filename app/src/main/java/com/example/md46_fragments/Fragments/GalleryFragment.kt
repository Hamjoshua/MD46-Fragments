package com.example.md46_fragments.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.md46_fragments.GIViewModel
import com.example.md46_fragments.GalleryImageClickHandler
import com.example.md46_fragments.ImageRecyclerView
import com.example.md46_fragments.R
import com.example.md46_fragments.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GalleryFragment : Fragment(), ChangeDetailsFragment.DescriptionChangeListener {
    private val viewModel: GIViewModel by viewModels()
    private val context = requireParentFragment().requireContext()
    private lateinit var binding : FragmentGalleryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentGalleryBinding.inflate(layoutInflater)

        initGallery()
        loadImages()
    }

    private fun initGallery() {
        binding.rList.adapter = ImageRecyclerView(requireParentFragment() as
                GalleryImageClickHandler, viewModel.listOfAllImages.value!!.toMutableList())
        binding.rList.layoutManager = GridLayoutManager(context, 3)
    }

    private fun loadImages() {
        viewModel.loadImages(requireParentFragment().requireContext())
        binding.rList.adapter?.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onChange(newDescription: String, imageId: Int) {
        viewModel.updateGalleryImage(newDescription, imageId)
    }

}