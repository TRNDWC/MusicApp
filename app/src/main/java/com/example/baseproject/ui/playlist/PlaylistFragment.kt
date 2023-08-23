package com.example.baseproject.ui.playlist

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.baseproject.R
import com.example.baseproject.data.model.LibraryItem
import com.example.baseproject.data.model.PlaylistSongItem
import com.example.baseproject.databinding.FragmentPlaylistBinding
import com.example.baseproject.databinding.PlaylistSongItemBinding
import com.example.baseproject.navigation.AppNavigation
import com.example.baseproject.service.MusicService
import com.example.baseproject.ui.playlist.addsong.AddSongDialog
import com.example.baseproject.ui.playlist.editplaylist.EditPlaylistDialog
import com.example.baseproject.utils.Random
import com.example.core.base.BaseFragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.AndroidEntryPoint
import java.io.InputStream
import java.util.Collections

import javax.inject.Inject


@AndroidEntryPoint
class PlaylistFragment :
    BaseFragment<FragmentPlaylistBinding, PlaylistViewModel>(R.layout.fragment_playlist),
    OnItemClickListener {
    @Inject
    lateinit var appNavigation: AppNavigation
    private val viewModel: PlaylistViewModel by activityViewModels()
    val item = arguments?.getParcelable<LibraryItem>("playlist")
    private var image: String? = null
    private var title: String = ""

    override fun getVM() = viewModel
    private val random = Random()
    private var musicService: MusicService? = null
    private var mSongList: List<PlaylistSongItem> = mutableListOf()
    private var mShuffleSongList: List<PlaylistSongItem> = mutableListOf()
    private lateinit var playlistAdapter: PlaylistSongItemAdapter
    private lateinit var materialToolbar: CollapsingToolbarLayout
    private var isServiceConnected: Boolean = false
    private lateinit var intent: Intent
    private lateinit var bundle: Bundle
    private var firstInit: Boolean = false
    private var previousClickedSong: PlaylistSongItem =
        PlaylistSongItem(songId = 0, songImage = "", songTitle = "", artists = "", resource = "")
    private val mServiceConnection: ServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(componentName: ComponentName?, iBinder: IBinder?) {
            Log.e("HoangDH", "Service Connected from PlayList")
            val myBinder: MusicService.MyBinder = iBinder as MusicService.MyBinder
            musicService = myBinder.getMyService()
            isServiceConnected = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isServiceConnected = false
        }

    }

    override fun setOnClick() {
        super.setOnClick()
        binding.addSong.setOnClickListener {
            viewModel.listAll()
            AddSongDialog(arguments?.getParcelable<LibraryItem>("playlist")!!.playlistId).show(
                childFragmentManager, "add_song"
            )
        }
        binding.btnEdit.setOnClickListener {
            viewModel.getSong(arguments?.getParcelable<LibraryItem>("playlist")!!.playlistId)
            var data = arguments?.getParcelable<LibraryItem>("playlist")!!
            if (image != null)
                data = LibraryItem(data.playlistId, data.playlistTitle, image)
            if (title != "")
                data.playlistTitle = title
            EditPlaylistDialog(
                data
            ).show(
                childFragmentManager, "edit_playlist"
            )
        }
        binding.btnPlaylistPlay.apply {
            viewModel.songList.observe(viewLifecycleOwner) { songList ->
                if (songList.isEmpty()) isEnabled = false
                else {
                    isEnabled = true
                    setOnClickListener {
                        val item = mSongList[0]
                        Log.e("HoangDH", "itemClicked")
                        prepareBundle(item)
                        Log.e("HoangDH", "$previousClickedSong")

                        if (!firstInit) {
                            requireActivity().startService(intent)
                            requireActivity().bindService(
                                intent,
                                mServiceConnection,
                                Context.BIND_AUTO_CREATE
                            )
                            firstInit = true
                            previousClickedSong = item
                            Log.e("HoangDH", "$firstInit")
                        } else if (previousClickedSong != item) {
                            requireActivity().stopService(intent)
                            requireActivity().unbindService(mServiceConnection)
                            requireActivity().startService(intent)
                            requireActivity().bindService(
                                intent,
                                mServiceConnection,
                                Context.BIND_AUTO_CREATE
                            )
                            previousClickedSong = item
                        }
                    }
                }

            }
        }
    }

    override fun bindingStateView() {
        super.bindingStateView()
        val item = arguments?.getParcelable<LibraryItem>("playlist")
        item?.let {
            viewModel.getSong(it.playlistId)
            viewModel.set(it.playlistId)
            viewModel.getData(it.playlistId)
        }
        recyclerviewAction()
        searchAction()

        // material tool bar
        materialToolbar = binding.collapsingToolbar
        materialToolbar.title = item?.playlistTitle

        binding.apply {
            nestedScrollView.visibility = View.GONE
            collapsingToolbar.visibility = View.GONE
            btnPlaylistPlay.visibility = View.GONE
            ProgressBar.visibility = View.VISIBLE
        }

        viewModel.cPlaylist.observe(viewLifecycleOwner) { item ->
            if (item?.playlistImage == null) {
                binding.playlistCover.background = resources.getDrawable(R.drawable.spotify)
                binding.collapsingToolbar.background = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    intArrayOf(Color.parseColor("#464545"), Color.BLACK)
                )
            } else {
                Glide.with(requireContext())
                    .load(item.playlistImage!!.toUri())
                    .into(binding.playlistCover)
                getBitmapFromFirebaseStorage(item.playlistImage!!) { bitmap ->
                    if (bitmap != null) {
                        binding.collapsingToolbar.background = getDominantColor(bitmap)

                        binding.apply {
                            nestedScrollView.visibility = View.VISIBLE
                            collapsingToolbar.visibility = View.VISIBLE
                            btnPlaylistPlay.visibility = View.VISIBLE
                            ProgressBar.visibility = View.GONE
                        }
                    } else {
                        binding.apply {
                            nestedScrollView.visibility = View.GONE
                            collapsingToolbar.visibility = View.GONE
                            btnPlaylistPlay.visibility = View.GONE
                            ProgressBar.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
        updateViewChange()
    }

    private fun updateViewChange() {
        viewModel.title.observe(viewLifecycleOwner) {
            if (it != "") {
                title = it
                item?.playlistTitle = it
                binding.collapsingToolbar.title = it
                viewModel.title.postValue("")
            }
        }
        viewModel.image.observe(viewLifecycleOwner) {
            if (it != null) {
                image = it
                item?.playlistImage = it
                binding.playlistCover.setImageURI(it.toUri())
                val `is`: InputStream? =
                    requireActivity().contentResolver.openInputStream(it.toUri())
                val bitmap = BitmapFactory.decodeStream(`is`)
                `is`?.close()
                binding.collapsingToolbar.background = getDominantColor(bitmap)
                viewModel.image.postValue(null)
            }
        }
    }

    private fun getBitmapFromFirebaseStorage(url: String, callback: (Bitmap?) -> Unit) {
        val storageReference: StorageReference =
            FirebaseStorage.getInstance().getReferenceFromUrl(url)

        val ONE_MEGABYTE: Long = 1024 * 1024
        storageReference.getBytes(ONE_MEGABYTE)
            .addOnSuccessListener { bytes ->
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                callback(bitmap)
            }
            .addOnFailureListener { exception ->
                // Handle failure
                callback(null)
            }
    }

    private fun prepareBundle(item: PlaylistSongItem) {
        bundle = Bundle()
        val position = viewModel.songList.value!!.indexOf(item)
        bundle.putInt("song_position", position)
        bundle.putParcelableArrayList(
            "song_list",
            viewModel.songList.value as ArrayList<PlaylistSongItem>)
        bundle.putParcelableArrayList(
            "shuffle_song_list",
            mShuffleSongList as ArrayList<PlaylistSongItem>
        )
        bundle.putParcelable("song_item", item)
        intent = Intent(context, MusicService::class.java)
        intent.putExtra("song_bundle", bundle)
    }

    private fun recyclerviewAction() {
        viewModel.songList.observe(viewLifecycleOwner) {
            mSongList = it
            playlistAdapter = PlaylistSongItemAdapter(mSongList, this)
            binding.rcvPlaylistSong.adapter = playlistAdapter
        }


        binding.rcvPlaylistSong.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun searchAction() {

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                playlistAdapter.setFilteredList(viewModel.filter(newText, mSongList))
                return true
            }
        })
    }

    private fun getDominantColor(bitmap: Bitmap?): GradientDrawable {
        val swatchesTemp = Palette.from(bitmap!!).generate().swatches
        val swatches: List<Palette.Swatch> = ArrayList(swatchesTemp)
        Collections.sort(
            swatches
        ) { swatch1, swatch2 -> swatch2.population - swatch1.population }
        val gd: GradientDrawable
        when (swatches.size) {
            0 -> gd = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(Color.BLACK, Color.BLACK)
            )

            else -> gd = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(swatches[0].rgb, Color.BLACK)
            )
        }
        gd.cornerRadius = 0f
        return gd
    }

    override fun onItemClicked(item: PlaylistSongItem, view: PlaylistSongItemBinding) {

        Log.e("HoangDH", "itemClicked")
        shuffleSong()
        prepareBundle(item)
        Log.e("HoangDH", "$previousClickedSong")

        if (!firstInit) {
            context?.startService(intent)
            context?.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
            firstInit = true
            previousClickedSong = item
            Log.e("HoangDH", "$firstInit")
        } else if (previousClickedSong != item) {
            musicService?.reset()
            context?.stopService(intent)
            context?.unbindService(mServiceConnection)
            context?.startService(intent)
            context?.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
            previousClickedSong = item
        }
    }
    private fun shuffleSong() {
        mShuffleSongList = random.getRandomSongList(mSongList)
    }
}