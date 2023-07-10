package com.example.baseproject.ui.play

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentPlayBinding
import com.example.baseproject.service.MusicService
import com.example.baseproject.ui.playlist.PlaylistSongItem
import com.example.core.base.BaseFragment
import dagger.hilt.android.qualifiers.ApplicationContext

class PlayFragment : BaseFragment<FragmentPlayBinding, PlayViewModel>(R.layout.fragment_play) {

    private lateinit var musicPlayer: MediaPlayer
    private var mPlayFragment: FragmentPlayBinding? = null
    private val viewModel: PlayViewModel by viewModels()
    override fun getVM() = viewModel
    private lateinit var description: String
    private lateinit var  playSongItem : PlaylistSongItem
    private lateinit var applicationContext: Context
    private lateinit var intent: Intent
    private lateinit var bundle : Bundle
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mPlayFragment = FragmentPlayBinding.inflate(inflater, container, false)
        musicPlayer = MediaPlayer.create(context, R.raw.querry_qnt)
        playSongItem = requireArguments().getSerializable("songItem") as PlaylistSongItem
        applicationContext = requireContext()

        bundle = Bundle()
        bundle.putSerializable("song_item", playSongItem)
        intent = Intent(context, MusicService::class.java)
        intent.putExtra("song_bundle", bundle)
        return mPlayFragment!!.root
    }

    override fun setOnClick() {
        super.setOnClick()
        autoPlay()
        mPlayFragment!!.btnPlay.setOnClickListener {
            if (!musicPlayer.isPlaying) {
                playSound()
                mPlayFragment!!.btnPlay.setImageResource(R.drawable.ic_pause)
                initSeekBar()
            } else {
                pauseSound()
                mPlayFragment!!.btnPlay.setImageResource(R.drawable.ic_green_play)
            }
        }
    }

    override fun bindingStateView() {
        super.bindingStateView()
        description = "${playSongItem.songTitle}\n${playSongItem.artists}"
        mPlayFragment!!.songDes.text = description
        mPlayFragment!!.seekBar.progress = musicPlayer.currentPosition
        mPlayFragment!!.seekBar.max = musicPlayer.duration
        mPlayFragment!!.seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    musicPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }

    private fun autoPlay(){
        applicationContext.startService(intent)
        mPlayFragment!!.btnPlay.setImageResource(R.drawable.ic_pause)

    }
    private fun playSound() {
        applicationContext.startService(intent)
    }

    private fun pauseSound() {
        musicPlayer.pause()
    }

    private fun initSeekBar() {
        mPlayFragment!!.seekBar.max = musicPlayer.duration
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                mPlayFragment!!.seekBar.progress = musicPlayer.currentPosition
                handler.postDelayed(this, 1000)
            }

        }, 0)
    }

    override fun onStop() {
        super.onStop()
        val intent = Intent(requireContext(), MusicService::class.java)
        applicationContext.stopService(intent)
    }
}