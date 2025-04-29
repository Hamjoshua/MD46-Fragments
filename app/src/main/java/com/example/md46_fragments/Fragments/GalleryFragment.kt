package com.example.md46_fragments.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.md46_fragments.DataClasses.GalleryImage
import com.example.md46_fragments.GIViewModel
import com.example.md46_fragments.GalleryImageClickHandler
import com.example.md46_fragments.ImageRecyclerView
import com.example.md46_fragments.R
import com.example.md46_fragments.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GalleryFragment : Fragment(), ChangeDetailsFragment.DescriptionChangeListener,
    GalleryImageClickHandler {
    private lateinit var navController: NavController
    private val viewModel: GIViewModel by viewModels()
    private lateinit var binding: FragmentGalleryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentGalleryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment =
            parentFragment as NavHostFragment
        navController = navHostFragment.navController

        setupObservers()
        loadImages()
    }

    private fun setupObservers() {
        viewModel.listOfAllImages.observe(viewLifecycleOwner) { images ->
            images?.let {
                binding.rList.adapter = ImageRecyclerView(this, it.toMutableList())
                binding.rList.layoutManager = GridLayoutManager(context, 3)
            }
        }
    }

    private fun initGallery() {
        val images = viewModel.listOfAllImages.value!!.toMutableList()

        binding.rList.adapter = ImageRecyclerView(
            this,
            images
        )
        binding.rList.layoutManager = GridLayoutManager(context, 3)
    }

    private fun loadImages() {
        viewModel.loadImages(requireParentFragment().requireContext())
        binding.rList.adapter?.notifyDataSetChanged()
    }

    override fun onChange(newDescription: String, imageId: Int) {
        viewModel.updateGalleryImage(newDescription, imageId)
    }

    override fun onClick(image: GalleryImage) {
        val direction = GalleryFragmentDirections.actionGalleryFragmentToDetailsFullscreenFragment(
            link = image.link,
            description = image.description
        )

        navController.navigate(direction)
    }

    override fun onLongClick(description: String, imageId: Int): Boolean {
        val direction = GalleryFragmentDirections.actionGalleryFragmentToChangeDetailsFragment(
            id = imageId,
            description = description
        )

        navController.navigate(direction)

        return true
    }

}