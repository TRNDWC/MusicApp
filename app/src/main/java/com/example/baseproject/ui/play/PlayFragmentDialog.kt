package com.example.baseproject.ui.play

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.SeekBar
import androidx.core.net.toUri
import com.example.baseproject.R
import com.example.baseproject.data.model.PlaylistSongItem
import com.example.baseproject.databinding.FragmentPlayDialogBinding
import com.example.baseproject.service.MusicService
import com.example.core.base.BaseFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PlayFragmentDialog : BottomSheetDialogFragment() {
    private lateinit var dialogBinding: FragmentPlayDialogBinding
    private lateinit var musicService : MusicService
    private var isServiceConnected: Boolean = false
    private lateinit var intent: Intent
    private lateinit var bundle: Bundle
    private var isPlaying: Boolean = false
    private var isLooping: Boolean = false
    val handler = Handler()
    private val mServiceConnection: ServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(componentName: ComponentName?, iBinder: IBinder?) {
            Log.e("HoangDH", "Service Connected")
            val myBinder: MusicService.MyBinder = iBinder as MusicService.MyBinder
            musicService = myBinder.getMyService()
            isServiceConnected = true
            initSeekBar()
            musicService.songLiveData.observe(viewLifecycleOwner) {
                bindingPlayerView(it)
            }
            musicService.songIsPlaying.observe(viewLifecycleOwner){
                isPlaying = it
                if (isPlaying) {
                    dialogBinding.btnPlay.setImageResource(R.drawable.ic_pause)
                } else {
                    dialogBinding.btnPlay.setImageResource(R.drawable.ic_green_play)
                }
            }
            setOnClick()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isServiceConnected = false
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                setupFullHeight(it)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialogBinding = FragmentPlayDialogBinding.inflate(inflater, container, false)
        val intent = Intent(context, MusicService::class.java)
        context?.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
        dialogBinding.btnPlay.setImageResource(R.drawable.ic_pause)
        dialogBinding.btnDown.setOnClickListener {
            dialog!!.dismiss()
        }
        dialogBinding.btnRepeat.setOnClickListener {
            isLooping = when(isLooping){
                true -> {
                    dialogBinding.btnRepeat.setImageResource(R.drawable.ic_repeat)
                    false
                }
                false -> {
                    dialogBinding.btnRepeat.setImageResource(R.drawable.ic_clicked_repeat)
                    true
                }
            }
            musicService.repeatMusic(isLooping)

        }
        return dialogBinding.root
    }

    private fun setOnClick(){
        dialogBinding.btnPlay.setOnClickListener {
            if (!isPlaying) {
                playSound()
                dialogBinding.btnPlay.setImageResource(R.drawable.ic_pause)
            } else {
                pauseSound()
                dialogBinding.btnPlay.setImageResource(R.drawable.ic_green_play)
            }
        }
        dialogBinding.btnNext.setOnClickListener {
            when(musicService.songPosition < musicService.songList.size - 1){
                true -> musicService.songPosition++
                false -> musicService.songPosition = 0
            }
            prepareBundle()
            bindingPlayerView(musicService.songList[musicService.songPosition])
            startMusicService()
        }
        dialogBinding.btnPre.setOnClickListener {
            when(musicService.songPosition > 0){
                true -> musicService.songPosition--
                false -> musicService.songPosition = musicService.songList.size - 1
            }
            prepareBundle()
            bindingPlayerView(musicService.songList[musicService.songPosition])
            startMusicService()
        }
    }

    private fun playSound() {
        musicService.startMusic()
    }

    private fun pauseSound() {
        musicService.pauseMusic()
    }

    @SuppressLint("SetTextI18n")
    private fun bindingPlayerView(song : PlaylistSongItem){
        dialogBinding.songImage.setImageURI(song.songImage!!.toUri())
        dialogBinding.songDes.text = "${song.songTitle}\n${song.artists}"
    }

    private fun prepareBundle() {
        bundle = Bundle()
        bundle.putInt("song_position", musicService.songPosition)
        bundle.putParcelableArrayList("song_list", ArrayList(musicService.songList))
        bundle.putParcelable("song_item", musicService.songList[musicService.songPosition])
        intent = Intent(context, MusicService::class.java)
        intent.putExtra("song_bundle", bundle)
    }

    private fun startMusicService() {
        // stop previous song
        context?.stopService(intent)
        context?.unbindService(mServiceConnection)
        //start next song
        context?.startService(intent)
        context?.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
        dialogBinding.btnPlay.setImageResource(R.drawable.ic_pause)
    }

    private fun initSeekBar() {
        Log.e("HoangDH", "initSeekBar")
        dialogBinding.seekBar.progress = musicService.currentPosition()
        dialogBinding.seekBar.max = musicService.duration()
        dialogBinding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    musicService.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        dialogBinding.seekBar.max = musicService.duration()

        handler.postDelayed(object : Runnable {
            override fun run() {
                dialogBinding.seekBar.progress = musicService.currentPosition()
                handler.postDelayed(this, 0)
            }
        }, 0)
    }
}