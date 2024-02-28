package com.example.baseproject.ui.home.hometab

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.baseproject.databinding.ChildLayoutBinding

class ChildAdapter(
    private val ChildItemList: List<ChildItem>,
    private val ParentItem: ParentItem,
    private val recyclerViewClickListener: RecyclerViewClickListener
)
    : RecyclerView.Adapter<ChildAdapter.ChildViewHolder>() {

    inner class ChildViewHolder(itemView: ChildLayoutBinding, listener: RecyclerViewClickListener) :
        RecyclerView.ViewHolder(itemView.root) {
        var ChildItemTitle: TextView = itemView.childDes
        var ChildItemImage: ImageView = itemView.childImg

        init {
            itemView.root.setOnClickListener {
                recyclerViewClickListener.onRecyclerViewItemClick(ParentItem, ChildItemList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val mChildItem: ChildLayoutBinding =
            ChildLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChildViewHolder(mChildItem, recyclerViewClickListener)
    }

    override fun getItemCount(): Int {
        return ChildItemList.size
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val user = ChildItemList[position]
        holder.ChildItemTitle.text = user.childItemTitle
        Glide.with(holder.itemView.context).load(user.childItemImage).into(holder.ChildItemImage)
    }

}