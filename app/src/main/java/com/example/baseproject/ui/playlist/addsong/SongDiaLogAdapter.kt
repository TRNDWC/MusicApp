package com.example.baseproject.ui.playlist.addsong

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproject.data.model.PlaylistSongItem
import com.example.baseproject.databinding.DialogSongItemBinding

interface OnItemClickListener {
    fun onItemClicked(itemId: Int, view: DialogSongItemBinding)
    fun onAddClicked(itemId: Int, view: DialogSongItemBinding)
}

class SongDiaLogAdapter(
    private var mSongList: List<PlaylistSongItem>,
    private val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<SongDiaLogAdapter.PlaylistSongItemViewHolder>() {

    fun setFilteredList(mList: List<PlaylistSongItem>) {
        this.mSongList = mList
        notifyDataSetChanged()
    }

    inner class PlaylistSongItemViewHolder(
        mPlaylistSongItem: DialogSongItemBinding,
        onItemClickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(mPlaylistSongItem.root) {
        val songItemTitle: TextView = mPlaylistSongItem.songTitle
        val songItemArtist: TextView = mPlaylistSongItem.songArtist
        val songImage: ImageView = mPlaylistSongItem.songImageView

        init {
            mPlaylistSongItem.apply {
                add.setOnClickListener {
                    onItemClickListener.onAddClicked(mSongList[adapterPosition].songId, mPlaylistSongItem)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistSongItemViewHolder {
        val mPlaylistSongItem: DialogSongItemBinding = DialogSongItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PlaylistSongItemViewHolder(mPlaylistSongItem, onItemClickListener)
    }

    override fun onBindViewHolder(holder: PlaylistSongItemViewHolder, position: Int) {
        val item = mSongList[position]
        holder.songItemTitle.text = item.songTitle
        holder.songItemArtist.text = item.artists
        holder.songImage.setImageURI(item.songImage?.toUri())
    }

    override fun getItemCount(): Int {
        return mSongList.size
    }
}