package com.achiang7.dotify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_ultimate_main.*

class NowPlayingFragment: Fragment() {

    companion object {
        val TAG: String = NowPlayingFragment::class.java.simpleName
        const val PLAYER_KEY = "player_key"
        private const val OUT_COUNT = "out_count"
    }

    private var song: Song? = null
    private var playCount: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.activity_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeMiniPlayer()
        updateSongView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            with(savedInstanceState) {
                playCount = getInt(OUT_COUNT, -1).toString()
            }
        } else {
            playCount = (1000..9999).random().toString()
        }

        arguments?.let { args ->
            val song = args.getParcelable<Song>(PLAYER_KEY)
            if (song != null) {
                this.song = song
            }
        }

        if (fragContainer != null) {
            fragContainer.removeAllViews()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        playCount?.let {
            outState.putInt(OUT_COUNT, it.toInt())
        }
        super.onSaveInstanceState(outState)
    }

    private fun initializeMiniPlayer() {
        numPlay.text = playCount

        play.setOnClickListener { view: View ->
            val newCount = Integer.parseInt(numPlay.text.toString()) + 1
            numPlay.text = newCount.toString()
        }
        previous.setOnClickListener { view: View ->
            Toast.makeText(context, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }
        next.setOnClickListener { view: View ->
            Toast.makeText(context, "Skipping to next track", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateSongView() {
        activity?.title = song?.title
        songTitle.text = song?.title
        artist.text = song?.artist
        song?.largeImageID?.let { albumImg.setImageResource(it) }
    }

    fun updateSong(song: Song) {
        this.song = song
        updateSongView()
    }
}