package com.example.baseproject.ui.playlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproject.data.PlaylistSongItem
import com.example.baseproject.databinding.PlaylistSongItemBinding
import com.example.baseproject.navigation.ItemClickNavigation

class PlaylistSongItemAdapter(private var playlistSongItem: List<PlaylistSongItem>) :
    RecyclerView.Adapter<PlaylistSongItemAdapter.PlaylistSongItemViewHolder>() {

    private lateinit var myListener: ItemClickNavigation

    fun setOnItemClickListener(listener: ItemClickNavigation) {
        myListener = listener
    }

    fun setFilteredList(mList: List<PlaylistSongItem>) {
        this.playlistSongItem = mList
        notifyDataSetChanged()
    }

    inner class PlaylistSongItemViewHolder(
        mPlaylistSongItem: PlaylistSongItemBinding,
        listener: ItemClickNavigation
    ) :
        RecyclerView.ViewHolder(mPlaylistSongItem.root) {
        val songItemTitle: TextView = mPlaylistSongItem.songTitle
        val songItemArtist: TextView = mPlaylistSongItem.songArtist
        val songImage: ImageView = mPlaylistSongItem.songImageView

        init {
            mPlaylistSongItem.playlistSongItem.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistSongItemViewHolder {
        val mPlaylistSongItem: PlaylistSongItemBinding = PlaylistSongItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PlaylistSongItemViewHolder(mPlaylistSongItem, myListener)
    }

    override fun onBindViewHolder(holder: PlaylistSongItemViewHolder, position: Int) {
        val item = playlistSongItem[position]
        holder.songItemTitle.text = item.songTitle
        holder.songItemArtist.text = item.artists
        holder.songImage.setImageResource(item.songImage)
    }

    override fun getItemCount(): Int {
        return playlistSongItem.size
    }
}