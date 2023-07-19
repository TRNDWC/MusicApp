package com.example.baseproject.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproject.databinding.SearchCategoryBinding
import com.example.baseproject.navigation.ItemClickNavigation


class CategoryAdapter(private val categoryList: List<SearchCategoryItem>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private lateinit var myListener: ItemClickNavigation
    var onItemClick: ((SearchCategoryItem) -> Unit)? = null


    inner class CategoryViewHolder(itemView: SearchCategoryBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val categoryTitle: TextView = itemView.searchCategory
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val mSearchCategoryItem: SearchCategoryBinding =
            SearchCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CategoryViewHolder(mSearchCategoryItem)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]
        holder.categoryTitle.text = category.itemTitle


        holder.itemView.setOnClickListener{
            onItemClick?.invoke(category)
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}

