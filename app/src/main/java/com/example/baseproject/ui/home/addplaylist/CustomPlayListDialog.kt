package com.example.baseproject.ui.home.addplaylist

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.viewModels
import com.example.baseproject.data.model.LibraryItem
import com.example.baseproject.databinding.CustomPlaylistDialogBinding
import com.example.baseproject.databinding.DialogSongItemBinding
import com.example.baseproject.ui.home.HomeViewModel
import com.example.baseproject.ui.playlist.addsong.OnItemClickListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class CustomPlaylistDialog(songId: Int) : BottomSheetDialogFragment(), OnItemClickListener {
    private lateinit var dialogBinding: CustomPlaylistDialogBinding
    private val viewModel: HomeViewModel by viewModels({ requireParentFragment() })
    private lateinit var playlistDialogAdapter: PlaylistDiaLogAdapter
    private lateinit var playlistList: List<LibraryItem>
    private val songId = songId
    fun getVM() = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

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
        dialogBinding = CustomPlaylistDialogBinding.inflate(inflater, container, false)
        return dialogBinding.root
    }

    override fun onItemClicked(position: Int, view: DialogSongItemBinding) {
        Log.d("trndwcs", "clicked")
    }

    override fun onAddClicked(itemId: Int, view: DialogSongItemBinding) {
        TODO("Not yet implemented")
    }
}