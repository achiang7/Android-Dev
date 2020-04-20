package com.achiang7.dotify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class SongListAdapter(private var songs: List<Song>):  RecyclerView.Adapter<SongListAdapter.SongListViewHolder>(){

    var onSongClickListener: ((song: Song) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return SongListViewHolder(view)
    }

    override fun getItemCount() = songs.size

    override fun onBindViewHolder(holder: SongListViewHolder, position: Int): Unit {
        val person = songs[position]
        holder.bind(person, position)
    }

    fun change(shuffledSongs: List<Song>) {

        val callback = SongDiffCallback(songs, shuffledSongs)
        val diffResult = DiffUtil.calculateDiff(callback)
        diffResult.dispatchUpdatesTo(this)

        songs = shuffledSongs

    }

    inner class SongListViewHolder(itemView: View):  RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.title)
        private val artist = itemView.findViewById<TextView>(R.id.artist)
        private val songImg = itemView.findViewById<ImageView>(R.id.ivSongImage)

        fun bind(song: Song, position: Int) {
            title.text = song.title
            artist.text = song.artist
            songImg.setImageResource(song.smallImageID)

            itemView.setOnClickListener {
                onSongClickListener?.invoke(song)
            }
        }
    }
}