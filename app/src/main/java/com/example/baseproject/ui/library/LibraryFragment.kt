package com.example.baseproject.ui.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentHomeTabBinding
import com.example.baseproject.databinding.FragmentLibraryBinding
import com.example.baseproject.ui.home.hometab.ParentAdapter
import com.example.core.base.BaseFragment

class LibraryFragment : BaseFragment<FragmentLibraryBinding, LibraryViewModel>(R.layout.fragment_library) {

    private var mLibraryFragment: FragmentLibraryBinding? = null
    companion object {
        fun newInstance() = LibraryFragment()
    }

    private val viewModel: LibraryViewModel by viewModels()
    override fun getVM(): LibraryViewModel {
        return viewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mLibraryFragment = FragmentLibraryBinding.inflate(inflater, container, false)

        val rcvUser : RecyclerView = mLibraryFragment!!.libraryRcv
        val layoutManager = LinearLayoutManager(
            mLibraryFragment!!.root.context , LinearLayoutManager.VERTICAL, false
        )

        val libraryAdapter = LibraryItemAdapter(
            LibraryItemList()
        )

        rcvUser.adapter=libraryAdapter
        rcvUser.layoutManager = layoutManager

        return mLibraryFragment!!.root
    }
    private fun LibraryItemList(): MutableList<LibraryItem>
    {
        val LibraryItemList: MutableList<LibraryItem> = ArrayList()
        LibraryItemList.add(LibraryItem("Card 1"))
        LibraryItemList.add(LibraryItem("Card 2"))
        LibraryItemList.add(LibraryItem("Card 3"))
        LibraryItemList.add(LibraryItem("Card 4"))
        return LibraryItemList
    }
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(LibraryViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

}