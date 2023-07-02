package com.example.baseproject.ui.search

import android.graphics.Color
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentSearchBinding
import com.example.baseproject.ui.home.HomeViewModel
import com.example.core.base.BaseFragment

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>(R.layout.fragment_search) {

    private var mSearchFragment: FragmentSearchBinding? = null

    companion object {
        fun newInstance() = SearchFragment()
    }
    private val viewModel: SearchViewModel by viewModels()
    override fun getVM() = viewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mSearchFragment = FragmentSearchBinding.inflate(inflater, container, false)
        val rcvSearchCategory: RecyclerView = mSearchFragment!!.rcvSearch
        val layoutManager = GridLayoutManager(mSearchFragment!!.root.context, 2)

        val searchCategoryAdapter = CategoryAdapter(dummyCategoryList())

        rcvSearchCategory.adapter = searchCategoryAdapter
        rcvSearchCategory.layoutManager = layoutManager
        return mSearchFragment!!.root
    }



    private fun dummyCategoryList(): List<SearchCategoryItem> {
        val itemList: MutableList<SearchCategoryItem> = ArrayList()
        val item1 = SearchCategoryItem("Pop")
        val item2 = SearchCategoryItem("Bollywood")
        val item3 = SearchCategoryItem("Podcasts")
        val item4 = SearchCategoryItem("Pop")
        val item5 = SearchCategoryItem("Pop")
        val item6 = SearchCategoryItem("Pop")
        val item7 = SearchCategoryItem("Pop")
        val item8 = SearchCategoryItem("Pop")
        val item9 = SearchCategoryItem("Pop")
        val item10 = SearchCategoryItem("Pop")
        val item11 = SearchCategoryItem("Pop")
        val item12 = SearchCategoryItem("Pop")
        itemList.add(item1)
        itemList.add(item2)
        itemList.add(item3)
        itemList.add(item4)
        itemList.add(item5)
        itemList.add(item6)
        itemList.add(item7)
        itemList.add(item8)
        itemList.add(item9)
        itemList.add(item10)
        itemList.add(item11)
        itemList.add(item12)
        return itemList
    }


}