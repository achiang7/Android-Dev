package com.achiang7.dotify

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_ultimate_main.*
import java.util.ArrayList

class UltimateMainActivity : AppCompatActivity(), OnSongClickListener {

    private var currSong: Song? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ultimate_main)

        // initializing the app with the SongListFragment
        var songListFragment = getSongListFragment()
        if (songListFragment == null) {
            songListFragment = SongListFragment()
            val argumentBundle = Bundle().apply {
                val tempSongList: List<com.ericchee.songdataprovider.Song> = SongDataProvider.getAllSongs()
                putParcelableArrayList(SongListFragment.SONG_LIST, convertSongs(tempSongList))
            }
            songListFragment.arguments = argumentBundle

            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, songListFragment, SongListFragment.TAG)
                .commit()
        }

        supportFragmentManager.addOnBackStackChangedListener {
            val hasBackStack = supportFragmentManager.backStackEntryCount > 0

            if (hasBackStack) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }

        // clicking on the shuffle button
        shuffleBtn.setOnClickListener {
            val songListFragment = getSongListFragment()
            songListFragment?.shuffleList()
        }

        // clicking on the mini-player
        miniPlayer.setOnClickListener {
            navigateToSong()
        }
    }

    private fun getSongListFragment() = supportFragmentManager.findFragmentByTag(SongListFragment.TAG) as? SongListFragment

    private fun getNowPlayingFragment() = supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) as? NowPlayingFragment

    override fun onSupportNavigateUp(): Boolean {
        miniPlayerContainer.visibility = View.VISIBLE
        supportFragmentManager.popBackStackImmediate()
        return super.onNavigateUp()
    }

    override fun onSongClicked(song: Song) {
        miniPlayer.text = song.title + " - " + song.artist
        currSong = song
    }

    private fun navigateToSong(){
        currSong?.let {
            miniPlayerContainer.visibility = View.GONE

            var nowPlayingFragment = getNowPlayingFragment()
            if (nowPlayingFragment == null) {
                nowPlayingFragment = NowPlayingFragment()
                val argumentBundle = Bundle().apply {
                    putParcelable(NowPlayingFragment.PLAYER_KEY, it)
                }
                nowPlayingFragment.arguments = argumentBundle

                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragContainer, nowPlayingFragment, NowPlayingFragment.TAG)
                    .addToBackStack(NowPlayingFragment.TAG)
                    .commit()
            } else {
                nowPlayingFragment.updateSong(it)
            }
        }
    }

    private fun convertSongs(tempSongList: List<com.ericchee.songdataprovider.Song>): ArrayList<Song>? {
        val songs: ArrayList<Song> = ArrayList()
        for (eSong in tempSongList) {
            val mySong: Song = Song(eSong.id,
                eSong.title,
                eSong.artist,
                eSong.durationMillis,
                eSong.smallImageID,
                eSong.largeImageID)
            songs.add(mySong)
        }
        return songs
    }

}
