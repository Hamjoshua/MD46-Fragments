package com.example.md46_fragments.Fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.md46_fragments.R
import com.example.md46_fragments.databinding.DialogEditTextBinding

class ChangeDetailsFragment : DialogFragment() {
    private lateinit var binding: DialogEditTextBinding
    private lateinit var listener : DescriptionChangeListener
    interface DescriptionChangeListener{
        fun onChange(newDescription: String, imageId: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as DescriptionChangeListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val description = arguments?.getString("description")
        val imageId = arguments?.getInt("imageId")
        binding = DialogEditTextBinding.inflate(layoutInflater)

        binding.editDescriptionTxt.setText(description)

        return AlertDialog.Builder(requireContext())
            .setMessage("Изменение описания")
            .setView(binding.root)
            .setPositiveButton(getString(R.string.save_dialog)) { _, _ ->
                listener.onChange(binding.editDescriptionTxt.text.toString(),
                    imageId!!)
            }
            .setNegativeButton(getString(R.string.cancel_dialog)) { _,_ -> }
            .create()
    }

}