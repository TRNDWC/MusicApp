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
    private var mPlayFragment: FragmentPlayBinding? = null

    private val viewModel: PlayViewModel by viewModels()
    override fun getVM() = viewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mPlayFragment = FragmentPlayBinding.inflate(inflater, container, false)
        val description: String = arguments?.getString("title").toString() +
                "\n" + arguments?.getString("artist").toString()
        mPlayFragment!!.songDes.text = description

        musicPlayer = MediaPlayer.create(context, R.raw.querry_qnt)
        mPlayFragment = FragmentPlayBinding.inflate(inflater, container, false)
        mPlayFragment!!.seekBar.progress = musicPlayer.currentPosition
        mPlayFragment!!.seekBar.max = musicPlayer.duration
        mPlayFragment!!.seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener{
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

        return mPlayFragment!!.root
    }
    

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun playSound() {
        musicPlayer.start()
    }

    private fun pauseSound() {
        musicPlayer.pause()
    }

    private fun initSeekBar(){
        mPlayFragment!!.seekBar.max= musicPlayer.duration
        val handler = Handler()
        handler.postDelayed(object : Runnable{
            override fun run() {
                mPlayFragment!!.seekBar.progress = musicPlayer.currentPosition
                handler.postDelayed(this, 1000)
            }

        }, 0)
    }

    override fun onStop() {
        super.onStop()

        musicPlayer.release()

    }
}