package com.achiang7.dotify

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListFragment: Fragment() {

    private lateinit var songListAdapter: SongListAdapter
    private lateinit var songs: ArrayList<Song>
    private var onSongClickListener: OnSongClickListener? = null

    companion object {
        val TAG: String = SongListFragment::class.java.simpleName
        const val SONG_LIST = "song_list"
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnSongClickListener) {
            onSongClickListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.activity_song_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.title = "All Songs"
        songs = arguments?.getParcelableArrayList<Song>(SONG_LIST) as ArrayList<Song>
        songListAdapter = SongListAdapter(songs)

        songListAdapter.onSongClickListener = { song: Song ->
            onSongClickListener?.onSongClicked(song) // move this line to when the miniplayer is clicked
        }

        rvSongs.adapter = songListAdapter

//        shuffleBtn.setOnClickListener { view: View ->
//            songListAdapter.change(songs.shuffled())
//        }
//
//        songInfo.setOnClickListener { view: View ->
//            val intent = Intent(context, MainActivity::class.java)
//            intent.putExtra(MainActivity.SONG_KEY, currSong)
//            startActivity(intent)
//        }
    }

    fun shuffleList() {
        songListAdapter.change(songs.shuffled())
    }

}