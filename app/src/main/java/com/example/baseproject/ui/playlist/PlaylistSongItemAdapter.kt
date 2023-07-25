package com.example.baseproject.ui.playlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproject.data.model.PlaylistSongItem
import com.example.baseproject.databinding.PlaylistSongItemBinding

class PlaylistSongItemAdapter(private var playlistSongItem: List<PlaylistSongItem>) :
    RecyclerView.Adapter<PlaylistSongItemAdapter.PlaylistSongItemViewHolder>() {

    var onItemClick: ((PlaylistSongItem) -> Unit)? = null


    fun setFilteredList(mList: List<PlaylistSongItem>) {
        this.playlistSongItem = mList
        notifyDataSetChanged()
    }

    inner class PlaylistSongItemViewHolder(
        mPlaylistSongItem: PlaylistSongItemBinding
    ) :
        RecyclerView.ViewHolder(mPlaylistSongItem.root) {
        val songItemTitle: TextView = mPlaylistSongItem.songTitle
        val songItemArtist: TextView = mPlaylistSongItem.songArtist
        val songImage: ImageView = mPlaylistSongItem.songImageView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistSongItemViewHolder {
        val mPlaylistSongItem: PlaylistSongItemBinding = PlaylistSongItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PlaylistSongItemViewHolder(mPlaylistSongItem)
    }

    override fun onBindViewHolder(holder: PlaylistSongItemViewHolder, position: Int) {
        val item = playlistSongItem[position]
        holder.songItemTitle.text = item.songTitle
        holder.songItemArtist.text = item.artists
        holder.songImage.setImageURI(item.songImage?.toUri())

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return playlistSongItem.size
    }
}