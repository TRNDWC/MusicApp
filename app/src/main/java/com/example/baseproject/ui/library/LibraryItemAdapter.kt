package com.example.baseproject.ui.library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproject.data.LibraryItem
import com.example.baseproject.databinding.LibraryItemBinding
import com.example.baseproject.navigation.ItemClickNavigation

class LibraryItemAdapter(
    private var libraryItemList: List<LibraryItem>,
    private val onItemClickListener: ItemClickNavigation
) :
    RecyclerView.Adapter<LibraryItemAdapter.LibraryItemViewHolder>() {

    private lateinit var myListener: ItemClickNavigation
    fun setOnItemClickListener(listener: ItemClickNavigation) {
        myListener = listener
    }

    inner class LibraryItemViewHolder(
        mLibraryItem: LibraryItemBinding
    ) :
        RecyclerView.ViewHolder(mLibraryItem.root) {
        var libraryItemTitle = mLibraryItem.libraryTitle

        init {
            mLibraryItem.libraryItem.setOnClickListener {
                onItemClickListener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryItemViewHolder {
        val mLibraryItem: LibraryItemBinding =
            LibraryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LibraryItemViewHolder(mLibraryItem)
    }

    override fun getItemCount(): Int {
        return libraryItemList.size
    }

    override fun onBindViewHolder(holder: LibraryItemViewHolder, position: Int) {
        val item = libraryItemList[position]
        holder.libraryItemTitle.text = item.playlistTitle
    }
}