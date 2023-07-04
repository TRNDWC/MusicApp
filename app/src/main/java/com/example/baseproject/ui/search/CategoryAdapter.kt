package com.example.baseproject.ui.search

import android.content.ClipData.Item
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproject.R
import com.example.baseproject.databinding.SearchCategoryBinding
import com.example.baseproject.navigation.AppNavigation
import com.example.baseproject.navigation.ItemClickNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


class CategoryAdapter(private val categoryList: List<SearchCategoryItem>): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private lateinit var myListener: ItemClickNavigation

    fun setOnItemClickListener(listener: ItemClickNavigation){
        myListener = listener
    }

    inner class CategoryViewHolder(itemView: SearchCategoryBinding, listener: ItemClickNavigation): RecyclerView.ViewHolder(itemView.root){
        val categoryTitle: TextView = itemView.searchCategory

        init {
            itemView.categoryCard.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val mSearchCategoryItem: SearchCategoryBinding = SearchCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CategoryViewHolder(mSearchCategoryItem, myListener)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]
        holder.categoryTitle.text = category.categoryTitle
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}

