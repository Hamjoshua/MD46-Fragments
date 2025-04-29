package com.example.md46_fragments.Fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.md46_fragments.R
import com.example.md46_fragments.databinding.DialogEditTextBinding

class ChangeDetailsFragment : DialogFragment() {
    private lateinit var binding: DialogEditTextBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val id: Int? = arguments?.getInt("id")
        val description: String? = arguments?.getString("description")

        binding = DialogEditTextBinding.inflate(layoutInflater)

        binding.editDescriptionTxt.setText(description)

        return AlertDialog.Builder(requireContext())
            .setMessage("Изменение описания")
            .setView(binding.root)
            .setPositiveButton(getString(R.string.save_dialog)) { _, _ ->
                returnResultBack(
                    binding.editDescriptionTxt.text.toString(),
                    id!!
                )
            }
            .setNegativeButton(getString(R.string.cancel_dialog)) { _, _ -> }
            .create()
    }

    fun returnResultBack(description: String, id: Int) {
        val result = Bundle().apply {
            putInt("id", id)
            putString("description", description)
        }
        parentFragmentManager.setFragmentResult("edit_description_request", result)
        dismiss()
    }

}