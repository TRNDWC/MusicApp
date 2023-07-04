package com.example.baseproject.ui.playlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproject.databinding.PlaylistSongItemBinding

class PlaylistSongItemAdapter(private val playlistSongItem: List<PlaylistSongItem> ) : RecyclerView.Adapter<PlaylistSongItemAdapter.PlaylistSongItemViewHolder>(){
    inner class PlaylistSongItemViewHolder(mPlaylistSongItem: PlaylistSongItemBinding) : RecyclerView.ViewHolder(mPlaylistSongItem.root){
        val songItemTitle: TextView = mPlaylistSongItem.songTitle
        val songItemArtist: TextView = mPlaylistSongItem.songArtist
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
    }

    override fun getItemCount(): Int {
        return playlistSongItem.size
    }
}