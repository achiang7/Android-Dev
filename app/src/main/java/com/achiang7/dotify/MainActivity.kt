package com.achiang7.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        const val SONG_KEY = "SONG_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        numPlay.text = (1000..9999).random().toString()

        play.setOnClickListener { view: View ->
            playBtnClicked(view)
        }

        previous.setOnClickListener { view: View ->
            prevBtnClicked(view)
        }

        next.setOnClickListener { view: View ->
            nextBtnClicked(view)
        }

        val song = intent.getParcelableExtra<Song>(SONG_KEY)
        insertSongInfo(song)
    }

    fun playBtnClicked(view: View) {
        val newCount = Integer.parseInt(numPlay.text.toString()) + 1
        numPlay.text = newCount.toString()
    }

    fun prevBtnClicked(view: View) {
        Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()

    }

    fun nextBtnClicked(view: View) {
        Toast.makeText(this, "Skipping to next track", Toast.LENGTH_SHORT).show()
    }

    fun changeUser(view: View) {
        if (cngBtn.text == "Apply") {
            userName.visibility = View.VISIBLE
            editBox.visibility = View.GONE
            userName.text = editBox.text
            cngBtn.text = "Change User"
        } else {
            cngBtn.text = "Apply"
            userName.visibility = View.GONE
            editBox.visibility = View.VISIBLE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed();
        return true;
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    fun insertSongInfo(song: Song) {
        songTitle.text = song.title
        artist.text = song.artist
        albumImg.setImageResource(song.largeImageID)
    }

}
