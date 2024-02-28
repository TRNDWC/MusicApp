package com.example.baseproject.ui.home.hometab

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.baseproject.R
import com.example.baseproject.data.model.ChartModel
import com.example.baseproject.data.model.LibraryItem
import com.example.baseproject.data.model.PlaylistSongItem
import com.example.baseproject.databinding.FragmentHomeTabBinding
import com.example.baseproject.navigation.AppNavigation
import com.example.baseproject.service.MusicService
import com.example.baseproject.ui.playlist.PlaylistViewModel
import com.example.baseproject.utils.Response
import com.example.core.base.BaseFragment
import com.example.core.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeTabFragment :
    BaseFragment<FragmentHomeTabBinding, HomeTabViewModel>(R.layout.fragment_home_tab),
    RecyclerViewClickListener {
    private lateinit var parentAdapter: ParentAdapter
    private val playListViewModel: PlaylistViewModel by activityViewModels()

    private val mServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName?, iBinder: IBinder?) {
            Log.e("HoangDH", "Service Connected from PlayList")
            val myBinder: MusicService.MyBinder = iBinder as MusicService.MyBinder
            playListViewModel.musicService.postValue(myBinder.getMyService())
        }

        override fun onServiceDisconnected(name: ComponentName?) {
        }
    }

    @Inject
    lateinit var appNavigation: AppNavigation
    private val viewModel: HomeTabViewModel by viewModels()
    override fun getVM() = viewModel
    override fun setOnClick() {
        super.setOnClick()
        binding.ProgressBar.visibility = android.view.View.VISIBLE
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        viewModel.getChart()
    }

    override fun bindingStateView() {
        super.bindingStateView()
        viewModel.profileResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Loading -> {}
                is Response.Failure -> {}
                is Response.Success -> {
                    Glide.with(requireContext())
                        .load(response.data.profilePictureUrl)
                        .into(binding.imgProfile)
                    binding.titleTv.text = getString(R.string.hello) + " ${response.data.name}"
                    binding.ProgressBar.visibility = android.view.View.GONE
                }
            }
        }

        viewModel.chartUpdate.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Loading -> {}
                is Response.Failure -> {}
                is Response.Success -> {
                    binding.ProgressBar.visibility = android.view.View.GONE
                    setUpParentItemList(response.data)
                    "data: ${response.data}".toast(requireContext())
                }
            }
        }
    }

    private fun setUpParentItemList(chart: ChartModel) {
        val parentItemList = mutableListOf<ParentItem>()
        chart.tracks?.let {
            parentItemList.add(it.getParentItem())
        }
        chart.albums?.let {
            parentItemList.add(it.getParentItem())
        }
        chart.artists?.let {
            parentItemList.add(it.getParentItem())
        }
        chart.playlists?.let {
            parentItemList.add(it.getParentItem())
        }
        chart.podcasts?.let {
            parentItemList.add(it.getParentItem())
        }
        parentAdapter = ParentAdapter(parentItemList, this)
        binding.rcvFrg.adapter = parentAdapter
        binding.rcvFrg.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL,
            false
        )
        playListViewModel.firstInit.postValue(false)
    }


    override fun onRecyclerViewItemClick(parentItem: ParentItem, childItem: ChildItem) {
        when (parentItem.parentItemTitle) {
            "Tracks" -> {
                val bundle = Bundle()
                bundle.putParcelable("song_item", childItem.toPlaylistSongItem())
                bundle.putParcelableArrayList(
                    "song_list",
                    arrayListOf(childItem.toPlaylistSongItem())
                )
                bundle.putParcelableArrayList(
                    "shuffle_song_list",
                    arrayListOf(childItem.toPlaylistSongItem())
                )
                bundle.putInt("position", 0)


                val intent = Intent(context, MusicService::class.java)
                intent.putExtra("song_bundle", bundle)

                if (!playListViewModel.firstInit.value!!) {
                    context?.startService(intent)
                    context?.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
                    playListViewModel.setFirstInit()
                } else {
                    playListViewModel.musicService.value?.reset()
                    context?.stopService(intent)
                    context?.startService(intent)
                }
            }

            "Albums" -> {
                val bundle = Bundle()
                bundle.putParcelable("playlist", childItem.toLibraryItem())
                findNavController().navigate(
                    R.id.action_homeTabFragment_to_playlistFragment,
                    bundle
                )
            }

            "Artists" -> {
//                val libraryItem = LibraryItem(
//                    playlistId = childItem.data!!,
//                    playlistTitle = childItem.childItemTitle,
//                    playlistImage = childItem.childItemImage
//                )
//                Log.d("HoangDH", "Artist: $libraryItem")
//                val bundle = Bundle()
//                bundle.putParcelable("playlist", libraryItem)
//                findNavController().navigate(
//                    R.id.action_homeTabFragment_to_playlistFragment,
//                    bundle
//                )
            }

            "Playlists" -> {
//                val bundle = Bundle()
//                bundle.putParcelable("playlist", childItem)
//                findNavController().navigate(
//                    R.id.action_homeTabFragment_to_playlistFragment,
//                    bundle
//                )
            }

            "Podcasts" -> {
//                val bundle = Bundle()
//                bundle.putParcelable("podcast", childItem)
//                findNavController().navigate(
//                    R.id.action_homeTabFragment_to_playlistFragment,
//                    bundle
//                )
            }
        }
    }
}