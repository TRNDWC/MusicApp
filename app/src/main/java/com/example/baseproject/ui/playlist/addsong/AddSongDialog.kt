package com.example.baseproject.ui.playlist.addsong

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseproject.R
import com.example.baseproject.data.model.PlaylistSongItem
import com.example.baseproject.databinding.AddSongLayoutBinding
import com.example.baseproject.databinding.DialogSongItemBinding
import com.example.baseproject.ui.playlist.PlaylistViewModel
import com.example.core.utils.toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddSongDialog(playlistId: Int) : BottomSheetDialogFragment(), OnItemClickListener {
    private lateinit var dialogBinding: AddSongLayoutBinding
    private val viewModel: PlaylistViewModel by viewModels({requireParentFragment()})
    private lateinit var songDialogAdapter: SongDiaLogAdapter
    private lateinit var songList : List<PlaylistSongItem>
    private val playlistId = playlistId
    fun getVM() = viewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchAction()
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
        dialogBinding = AddSongLayoutBinding.inflate(inflater, container, false)
        dialogBinding.searchView.setBackgroundResource(R.color.color_btn)
        viewModel.listAll()

        viewModel.addSongList.observe(viewLifecycleOwner) { newList ->
            songList = newList
            songDialogAdapter = SongDiaLogAdapter(songList,this)
            dialogBinding.rcvListSong.adapter = songDialogAdapter
            dialogBinding.rcvListSong.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        return dialogBinding.root
    }

    override fun onItemClicked(position: Int, view: DialogSongItemBinding) {
        Log.d("trndwcs","clicked")
    }

    override fun onAddClicked(position: Int, view: DialogSongItemBinding) {
        viewModel.addSongtoPlaylist(songList[position].songId, playlistId)
        viewModel.getSong(playlistId)
        "${position}clicked".toast(requireContext())
    }

    private fun searchAction() {
        dialogBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                songDialogAdapter.setFilteredList(viewModel.filter(newText,songList))
                return true
            }
        })
    }
}