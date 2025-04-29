package com.example.md46_fragments.Fragments

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.md46_fragments.databinding.FragmentDetailsFullscreenBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailsFullscreenFragment : Fragment() {
    private var _binding: FragmentDetailsFullscreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailsFullscreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageLink = arguments?.getString("uri") ?: return
        val description = arguments?.getString("description") ?: ""

        Glide.with(this)
            .load(Uri.parse(imageLink))
            .into(binding.actionImage)

        binding.fullscreenContent.text = description
        binding.dummyButton.setOnClickListener{
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}