package com.achiang7.dotify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.achiang7.dotify.MainActivity.Companion.SONG_KEY
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListActivity : AppCompatActivity() {

    var currSong: Song? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)

        title = "All Songs"

        val tempSongList: List<com.ericchee.songdataprovider.Song> = SongDataProvider.getAllSongs()
        val songs: List<Song> = convertSongs(tempSongList)
        val songListAdapter = SongListAdapter(songs)

        songListAdapter.onSongClickListener = { song: Song ->
            currSong = song
            songInfo.text = currSong?.title + " - " + currSong?.artist
        }

        rvSongs.adapter = songListAdapter

        shuffleBtn.setOnClickListener { view: View ->
            songListAdapter.change(songs.shuffled())
        }

        songInfo.setOnClickListener { view: View ->
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(SONG_KEY, currSong)
            startActivity(intent)
        }
    }

    // I wanted to use my own "Song" class.
    // If the library I'm calling change its class structure in the future, it would be much easier for me to
    // update accordingly here instead of having to find every single reference of com.ericchee.songdataprovider.Song and change their attributes.
    // Also com.ericchee.songdataprovider.Song is just a lengthy name and I don't want to write it out every time I declare Song
    private fun convertSongs(tempSongList: List<com.ericchee.songdataprovider.Song>): MutableList<Song> {
        val songs: MutableList<Song> = mutableListOf()
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
