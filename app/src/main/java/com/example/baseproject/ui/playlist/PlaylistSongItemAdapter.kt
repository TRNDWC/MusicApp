package com.example.baseproject.ui.playlist

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproject.databinding.PlaylistSongItemBinding
import com.example.baseproject.navigation.ItemClickNavigation

class PlaylistSongItemAdapter(private var playlistSongItem: List<PlaylistSongItem> ) : RecyclerView.Adapter<PlaylistSongItemAdapter.PlaylistSongItemViewHolder>(){

    var onItemClick : ((PlaylistSongItem) -> Unit )? = null
    inner class PlaylistSongItemViewHolder(mPlaylistSongItem: PlaylistSongItemBinding) :
        RecyclerView.ViewHolder(mPlaylistSongItem.root){
        val songItemTitle: TextView = mPlaylistSongItem.songTitle
        val songItemArtist: TextView = mPlaylistSongItem.songArtist
        val songImage : ImageView = mPlaylistSongItem.songImageView
    }

    fun setFilteredList (mList: List<PlaylistSongItem>){
        this.playlistSongItem = mList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistSongItemViewHolder {
        val mPlaylistSongItem : PlaylistSongItemBinding = PlaylistSongItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return PlaylistSongItemViewHolder(mPlaylistSongItem)
    }

    override fun onBindViewHolder(holder: PlaylistSongItemViewHolder, position: Int) {
        val item = playlistSongItem[position]
        holder.songItemTitle.text = item.songTitle
        holder.songItemArtist.text = item.artists
        holder.songImage.setImageResource(item.songImage)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(playlistSongItem[position])
        }
    }

    override fun getItemCount(): Int {
        return playlistSongItem.size
    }
}