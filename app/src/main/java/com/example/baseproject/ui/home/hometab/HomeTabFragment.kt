package com.example.baseproject.ui.home.hometab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentHomeTabBinding
import com.example.baseproject.navigation.AppNavigation
import com.example.core.base.BaseFragment
import com.example.core.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class HomeTabFragment :
    BaseFragment<FragmentHomeTabBinding, HomeTabViewModel>(R.layout.fragment_home_tab) {

    var mList = ParentItemList()
    private var mHometabFragment: FragmentHomeTabBinding? = null
    @Inject
    lateinit var appNavigation: AppNavigation

    companion object {
        fun newInstance() = HomeTabFragment()
    }

    private val viewModel: HomeTabViewModel by viewModels()
    override fun getVM() = viewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mHometabFragment = FragmentHomeTabBinding.inflate(inflater, container, false)

        val rcvUser: RecyclerView = mHometabFragment!!.rcvFrg
        val layoutManager = LinearLayoutManager(
            mHometabFragment!!.root.context, LinearLayoutManager.VERTICAL, false
        )

        val parentAdapter = ParentAdapter(
            mList
        )

        rcvUser.adapter = parentAdapter
        rcvUser.layoutManager = layoutManager
        val myItem = ParentItemList()
        parentAdapter.setRvItemClickListener(object : ParentAdapter.RvItemClickListener {
            override fun onChildItemClick(parentPosition: Int, childPosition: Int) {

                val bundle = Bundle()
                bundle.putString("title", getItem(myItem, parentPosition, childPosition)?.childItemTitle.toString()+"\n"+myItem[parentPosition].parentItemTitle.toString())
                appNavigation.openHomeScreentoPlaylistScreen(bundle)
            }

        })

        return mHometabFragment!!.root
    }

    private fun ParentItemList(): List<ParentItem> {
        val itemList: MutableList<ParentItem> = ArrayList()
        val item = ParentItem(
            "Title 1",
            ChildItemList()
        )
        itemList.add(item)
        val item1 = ParentItem(
            "Title 2",
            ChildItemList()
        )
        itemList.add(item1)
        val item2 = ParentItem(
            "Title 3",
            ChildItemList()
        )
        itemList.add(item2)
        val item3 = ParentItem(
            "Title 4",
            ChildItemList()
        )
        itemList.add(item3)
        return itemList
    }

    // Method to pass the arguments
    // for the elements
    // of child RecyclerView
    private fun ChildItemList(): List<ChildItem> {
        val ChildItemList: MutableList<ChildItem> = ArrayList()
        ChildItemList.add(ChildItem("Card 1"))
        ChildItemList.add(ChildItem("Card 2"))
        ChildItemList.add(ChildItem("Card 3"))
        ChildItemList.add(ChildItem("Card 4"))
        return ChildItemList
    }

    private fun get(listC: List<ChildItem>, position: Int): ChildItem? {
        return listC[position]
    }

    private fun getItem(listP: List<ParentItem>, parentP: Int, childP: Int): ChildItem? {
        var mChildItem: ChildItem? = null
        mChildItem = get(listP[parentP].childItemList, childP)
        return mChildItem
    }

}