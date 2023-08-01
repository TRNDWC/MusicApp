package com.example.baseproject.ui.home

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.baseproject.R
import com.example.baseproject.data.model.PlaylistSongItem
import com.example.baseproject.databinding.FragmentHomeBinding
import com.example.baseproject.navigation.AppNavigation
import com.example.baseproject.service.MusicService
import com.example.core.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    @Inject
    lateinit var appNavigation: AppNavigation

    private var musicService: MusicService? = null
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var intent: Intent
    private val mServiceConnection: ServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(componentName: ComponentName?, iBinder: IBinder?) {
            Log.e("HoangDH", "Service Connected")
            val myBinder: MusicService.MyBinder = iBinder as MusicService.MyBinder
            musicService = myBinder.getMyService()
            musicService!!.songLiveData.observe(viewLifecycleOwner) {
                bindingBottomMusicPlayer(it)
            }
            isServiceConnected = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isServiceConnected = false
        }

    }
    private var isServiceConnected: Boolean = false

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        Log.e("HoangDH", "initView")
        setupBottomNavigationBar()
        intent = Intent(context, MusicService::class.java)
        context?.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun bindingStateView() {
        super.bindingStateView()

    }

    private fun setupBottomNavigationBar() {
        val navHostFragment = childFragmentManager.findFragmentById(
            R.id.nav_host_container
        ) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)
    }

    override fun getVM() = viewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.tag("VietBH").d("A   " + "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag("VietBH").d("A   " + "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.tag("VietBH").d("A   " + "onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.tag("VietBH").d("A   " + "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        Timber.tag("VietBH").d("A   " + "onStart")
        super.onStart()
    }

    override fun onResume() {
        Timber.tag("VietBH").d("A   " + "onResume")
        super.onResume()

    }

    private fun bindingBottomMusicPlayer(songItem: PlaylistSongItem) {
        binding.songImage.setImageURI(songItem.songImage!!.toUri())
        binding.songTitle.text = songItem.songTitle
        binding.songArtist.text = songItem.artists
        binding.bottomMusicPlayer.visibility = View.VISIBLE
        Log.e(
            "HoangDH", "BindingBottomMusicPlayer"
        )
    }

    override fun onPause() {
        Timber.tag("VietBH").d("A   " + "onPause")
        super.onPause()
    }

    override fun onStop() {
        Timber.tag("VietBH").d("A   " + "onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Timber.tag("VietBH").d("A   " + "onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Timber.tag("VietBH").d("A   " + "onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Timber.tag("VietBH").d("A   " + "onCreate")
        super.onDetach()
    }


}