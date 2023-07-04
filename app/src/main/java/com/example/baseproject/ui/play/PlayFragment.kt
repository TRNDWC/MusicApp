package com.example.baseproject.ui.play

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.viewModels
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentPlayBinding
import com.example.core.base.BaseFragment

class PlayFragment : BaseFragment<FragmentPlayBinding, PlayViewModel>(R.layout.fragment_play) {

    private lateinit var musicPlayer: MediaPlayer
    private var playBinding: FragmentPlayBinding? = null

    companion object {
        fun newInstance() = PlayFragment()
    }

    private val viewModel: PlayViewModel by viewModels()
    override fun getVM() = viewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        musicPlayer = MediaPlayer.create(context, R.raw.querry_qnt)
        playBinding = FragmentPlayBinding.inflate(inflater, container, false)
        playBinding!!.seekBar.progress = musicPlayer.currentPosition
        playBinding!!.seekBar.max = musicPlayer.duration
        playBinding!!.seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser){
                    musicPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        playBinding!!.btnPlay.setOnClickListener {
            if (!musicPlayer.isPlaying) {
                playSound()
                playBinding!!.btnPlay.setImageResource(R.drawable.ic_pause)
                initSeekBar()
            } else {
                pauseSound()
                playBinding!!.btnPlay.setImageResource(R.drawable.ic_green_play)
            }
        }

        return playBinding!!.root
    }

    private fun playSound() {
        musicPlayer.playbackParams.speed = 0.5F
        musicPlayer.start()
    }

    private fun pauseSound() {
        musicPlayer.pause()
    }

    private fun initSeekBar(){
        playBinding!!.seekBar.max= musicPlayer.duration
        val handler = Handler()
        handler.postDelayed(object : Runnable{
            override fun run() {
                playBinding!!.seekBar.progress = musicPlayer.currentPosition
                handler.postDelayed(this, 1000)
            }

        }, 0)
    }

    override fun onStop() {
        super.onStop()

        musicPlayer.release()

    }
}