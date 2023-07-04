package com.example.baseproject.ui.library

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproject.databinding.LibraryItemBinding

class LibraryItemAdapter(private val LibraryItemList: List<LibraryItem>) : RecyclerView.Adapter<LibraryItemAdapter.LibraryItemViewHolder>(){
    inner class LibraryItemViewHolder(mLibraryItem: LibraryItemBinding) :
        RecyclerView.ViewHolder(mLibraryItem.root) {
            var libraryItemTitle: TextView
            init {
                libraryItemTitle = mLibraryItem.libraryTitle
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryItemViewHolder {
        val mLibraryItem : LibraryItemBinding = LibraryItemBinding.
        inflate(LayoutInflater.from(parent.context),parent,false)
        return LibraryItemViewHolder(mLibraryItem)
    }

    override fun getItemCount(): Int {
        return LibraryItemList.size
    }

    override fun onBindViewHolder(holder: LibraryItemViewHolder, position: Int) {
        val item = LibraryItemList[position]
        holder.libraryItemTitle.text = item.ItemTitle
    }
}