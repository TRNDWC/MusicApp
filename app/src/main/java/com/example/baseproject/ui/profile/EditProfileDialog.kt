package com.example.baseproject.ui.profile

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentEditProfileDialogBinding
import com.example.baseproject.extension.validate
import com.example.core.utils.toast
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class EditProfileDialog(private val userName: String) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentEditProfileDialogBinding
    private val viewModel: ProfileViewModel by activityViewModels()
    private var newImage: String? = null
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
                    } else {
                        viewModel.setValidState(true)
                    }
                }
                setText(userName)
            }

            imgProfile.setOnClickListener {
                ImagePicker.with(this@EditProfileDialog)
                    .compress(1024)
                    .crop()
                    .maxResultSize(
                        1080,
                        1080
                    )
                    .createIntent { intent ->
                        startForProfileImageResult.launch(intent)
                    }
            }

        }

        viewModel.apply {
            validator.observe(viewLifecycleOwner) { validator ->
                binding.btnSave.isEnabled = validator
            }

        }
        return binding.root

    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!
                newImage = fileUri.toString()
                binding.imgProfile.setImageURI(fileUri)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                ImagePicker.getError(data).toast(requireContext())
            } else {
                "Task Cancelled".toast(requireContext())
            }
        }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }
}