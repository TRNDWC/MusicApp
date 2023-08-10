package com.example.baseproject.ui.profile

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentEditProfileDialogBinding
import com.example.baseproject.extension.validate
import com.example.baseproject.utils.Response
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class EditProfileDialog(private val userName : String): BottomSheetDialogFragment() {

    private lateinit var binding: FragmentEditProfileDialogBinding
    private val viewModel: ProfileViewModel by activityViewModels()
    fun getVM() = viewModel
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                setupFullHeight(it)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("EditProfileDialog", "${userName}")
        binding = FragmentEditProfileDialogBinding.inflate(inflater, container, false)
        binding.apply {

            btnCancel.setOnClickListener {
                dismiss()
            }
            btnSave.setOnClickListener {
                viewModel.updateProfile(binding.etEditUsername.text.toString())
                dismiss()
            }
            etEditUsername.apply {
                validate { username ->
                    if (username.isEmpty()) {
                        binding.etEditUsername.error = getString(R.string.do_not_let_username_empty)
                        viewModel.setValidState(false)
                    }
                    else {
                        viewModel.setValidState(true)
                    }
                }
                setText(userName)
            }

        }

        viewModel.apply {
            validator.observe(viewLifecycleOwner){validator ->
                binding.btnSave.isEnabled = validator
            }

        }
        return binding.root

    }
}