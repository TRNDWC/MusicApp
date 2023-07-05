package com.example.baseproject.ui.home.hometab

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproject.databinding.ChildLayoutBinding

class ChildAdapter(private val ChildItemList: List<ChildItem>) : RecyclerView.Adapter<ChildAdapter.ChildViewHolder>(){

    private lateinit var myListener: OnItemClickListener
    interface OnItemClickListener {
        fun onItemClick(childPosition: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        myListener = listener
    }

    inner class ChildViewHolder(itemView: ChildLayoutBinding,
                                listener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView.root) {
        var ChildItemTitle: TextView = itemView.childDes

        init {
            itemView.childItem.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val mChildItem : ChildLayoutBinding = ChildLayoutBinding.
        inflate(LayoutInflater.from(parent.context),parent,false)
        return ChildViewHolder(mChildItem, myListener)
    }

    override fun getItemCount(): Int {
        return ChildItemList.size
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val user =ChildItemList[position]
        holder.ChildItemTitle.text=user.childItemTitle
    }

}