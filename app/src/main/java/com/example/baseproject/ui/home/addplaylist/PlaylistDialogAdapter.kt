package com.example.baseproject.ui.home.addplaylist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproject.data.model.LibraryItem
import com.example.baseproject.databinding.DialogPlaylistItemBinding

interface OnItemClickListener {
    fun onBtnClicked(state: Int, position: Int)
}

class PlaylistDiaLogAdapter(
    private var mSongList: List<LibraryItem>,
    private val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<PlaylistDiaLogAdapter.AddPlaylistViewHolder>() {

    inner class AddPlaylistViewHolder(
        mPlaylist: DialogPlaylistItemBinding,
        onItemClickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(mPlaylist.root) {
        val playlistItemTitle: TextView = mPlaylist.playlistTitle
        val playlistImage: ImageView = mPlaylist.playlistImageView

        init {
            mPlaylist.apply {
                btnAction.setOnClickListener {
                    onItemClickListener.onBtnClicked(0, adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddPlaylistViewHolder {
        val mPlaylist: DialogPlaylistItemBinding = DialogPlaylistItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return AddPlaylistViewHolder(mPlaylist, onItemClickListener)
    }

    override fun onBindViewHolder(holder: AddPlaylistViewHolder, position: Int) {
        val item = mSongList[position]
        holder.playlistImage.setImageURI(item.playlistImage?.toUri())
        holder.playlistItemTitle.text = item.playlistTitle
    }

    override fun getItemCount(): Int {
        return mSongList.size
    }
}