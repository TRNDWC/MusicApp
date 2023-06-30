package com.example.baseproject.ui.home.hometab

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproject.databinding.ChildLayoutBinding

class ChildAdapter(private val ChildItemList: List<ChildItem>) : RecyclerView.Adapter<ChildAdapter.ChildViewHolder>(){


    inner class ChildViewHolder(itemView: ChildLayoutBinding) :
        RecyclerView.ViewHolder(itemView!!.root) {
        var ChildItemTitle: TextView

        init {
            ChildItemTitle = itemView.childDes
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val mChildItem : ChildLayoutBinding = ChildLayoutBinding.
        inflate(LayoutInflater.from(parent.context),parent,false)
        return ChildViewHolder(mChildItem)
    }

    override fun getItemCount(): Int {
        return ChildItemList.size
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val user =ChildItemList[position]
        holder.ChildItemTitle.text=user.childItemTitle
    }

}