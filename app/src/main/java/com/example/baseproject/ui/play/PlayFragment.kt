package com.example.baseproject.ui.play

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.widget.SeekBar
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import com.example.baseproject.R
import com.example.baseproject.data.model.LibraryItem
import com.example.baseproject.data.model.PlaylistSongItem
import com.example.baseproject.databinding.FragmentPlayBinding
import com.example.baseproject.service.MusicService
import com.example.baseproject.ui.home.customplaylist.CustomPLaylistDialog
import com.example.baseproject.ui.playlist.addsong.AddSongDialog
import com.example.core.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlayFragment : BaseFragment<FragmentPlayBinding, PlayViewModel>(R.layout.fragment_play) {

    private var musicService: MusicService? = null
    private val viewModel: PlayViewModel by viewModels()
    override fun getVM() = viewModel
    private var playSongPosition: Int = 0
    private lateinit var songList: List<PlaylistSongItem>
    private lateinit var intent: Intent
    private lateinit var bundle: Bundle
    private lateinit var tPLaylistList: List<Int>
    val handler = Handler()
    private val mServiceConnection: ServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(componentName: ComponentName?, iBinder: IBinder?) {
            Log.e("HoangDH", "Service Connected")
            val myBinder: MusicService.MyBinder = iBinder as MusicService.MyBinder
            musicService = myBinder.getMyService()
            isServiceConnected = true
            initSeekBar()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isServiceConnected = false
        }

    }
    private var isServiceConnected: Boolean = false


    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        Log.e("HoangDH", "initView")
        songList = requireArguments().getParcelableArrayList("list_song")!!
        playSongPosition = requireArguments().getInt("position")

        prepareBundle(playSongPosition)
        context?.startService(intent)
        context?.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
        binding.btnPlay.setImageResource(R.drawable.ic_pause)
    }

    override fun setOnClick() {
        super.setOnClick()
        Log.e("HoangDH", "setOnClick")
        binding.btnPlay.setOnClickListener {
            Log.e("HoangDH", "isPlaying is ${musicService!!.isPlaying()}")
            if (!musicService!!.isPlaying()) {
                playSound()
                binding.btnPlay.setImageResource(R.drawable.ic_pause)
            } else {
                pauseSound()
                binding.btnPlay.setImageResource(R.drawable.ic_green_play)
            }
        }

        binding.btnNext.setOnClickListener {
            when (playSongPosition < songList.size - 1) {
                true -> ++playSongPosition
                false -> playSongPosition = 0
            }
            prepareBundle(playSongPosition)
            setSongDescription()
            startMusicService()
        }

        binding.btnPre.setOnClickListener {
            when (playSongPosition > 0) {
                true -> --playSongPosition
                false -> playSongPosition = songList.size - 1
            }
            prepareBundle(playSongPosition)
            setSongDescription()
            startMusicService()
        }

        binding.btnFav.setOnClickListener {
            CustomPLaylistDialog(songList[playSongPosition].songId, tPLaylistList).show(
                childFragmentManager,
                "custom_playlist"
            )
        }
    }

    override fun bindingStateView() {
        super.bindingStateView()
        setSongDescription()

        Log.e("HoangDH", "bindingStateView")
    }

    private fun setSongDescription() {
        viewModel.songDescription.value = "${songList[playSongPosition].songTitle}\n${
            songList[playSongPosition].artists
        }"
        binding.songDes.text = viewModel.songDescription.value
        binding.songImage.setImageURI(songList[playSongPosition].songImage?.toUri())
        viewModel.getPlaylistOfSong(songList[playSongPosition].songId)
        viewModel.tPlaylistList.observe(viewLifecycleOwner){newList ->
            tPLaylistList = newList
        }
//        binding.playBg.setBackgroundColor(Color.BLUE)
//        Blurry.with(context)
//            .radius(10)
//            .sampling(8)
//            .color(Color.argb(66, 255, 255, 0))
//            .async()
//            .animate(500)
//            .onto(binding.playBg as ViewGroup)
    }

    override fun onStart() {
        Log.e("HoangDH", "onStart")
        super.onStart()

    }

    override fun onResume() {
        super.onResume()
        Log.e("HoangDH", "onResume")

    }

    override fun onPause() {
        super.onPause()
        Log.e("HoangDH", "onPause")

    }

    override fun onStop() {
        super.onStop()
        Log.e("HoangDH", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
        Log.e("HoangDH", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.e("HoangDH", "onDestroy")
    }

    private fun playSound() {
        musicService!!.startMusic(songList[playSongPosition])
    }

    private fun pauseSound() {
        musicService!!.pauseMusic()
    }

    private fun initSeekBar() {
        Log.e("HoangDH", "initSeekBar")
        binding.seekBar.progress = musicService!!.currentPosition()
        binding.seekBar.max = musicService!!.duration()
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    musicService!!.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        binding.seekBar.max = musicService!!.duration()

        handler.postDelayed(object : Runnable {
            override fun run() {
                binding.seekBar.progress = musicService!!.currentPosition()
                handler.postDelayed(this, 0)
            }
        }, 0)
    }

    private fun startMusicService() {
        context?.startService(intent)
        context?.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
        binding.btnPlay.setImageResource(R.drawable.ic_pause)
    }

    private fun prepareBundle(songPosition: Int) {
        Log.e("HoangDH", "prepareBundle")
        Log.e("HoangDH", "${songList.size}")
        Log.e("HoangDH", "${playSongPosition}")
        bundle = Bundle()
        bundle.putParcelable("song_item", songList[songPosition])
        intent = Intent(context, MusicService::class.java)
        intent.putExtra("song_bundle", bundle)
    }

}