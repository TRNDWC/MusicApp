package com.example.baseproject.ui.home.customplaylist

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseproject.data.model.LibraryItem
import com.example.baseproject.databinding.CustomPlaylistDialogBinding
import com.example.baseproject.databinding.DialogPlaylistItemBinding
import com.example.baseproject.ui.home.HomeViewModel
import com.example.core.utils.toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CustomPLaylistDialog(songId: Int, tPLaylistList: List<Int>) :
    BottomSheetDialogFragment(), OnItemClickListener {
    private lateinit var dialogBinding: CustomPlaylistDialogBinding
    private val viewModel: HomeViewModel by viewModels({ requireParentFragment() })
    private lateinit var playlistDiaLogAdapter: PlaylistDialogAdapter
    private lateinit var playlistList: List<LibraryItem>
    private val tPLaylist = tPLaylistList

    val songId = songId
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
        dialogBinding = CustomPlaylistDialogBinding.inflate(inflater, container, false)
        viewModel.getPlaylistOfSong(songId)
        viewModel.listAll()

        viewModel.playlistList.observe(viewLifecycleOwner) { newList ->
            playlistList = newList
            playlistDiaLogAdapter = PlaylistDialogAdapter(playlistList, tPLaylist, this)
            dialogBinding.rcvListPlaylist.adapter = playlistDiaLogAdapter
            dialogBinding.rcvListPlaylist.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
        return dialogBinding.root
    }

    override fun onItemClicked(view: DialogPlaylistItemBinding) {
        view.playlistTitle.text.toString().toast(requireContext())
    }
}