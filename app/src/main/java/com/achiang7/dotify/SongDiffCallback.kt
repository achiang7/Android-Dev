package com.achiang7.dotify

import androidx.recyclerview.widget.DiffUtil

/**
 * Used for animation changes
 */
class SongDiffCallback(
    private val oldSong: List<Song>,
    private val newSong: List<Song>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldSong.size

    override fun getNewListSize(): Int = newSong.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldPerson = oldSong[oldItemPosition]
        val newPerson = newSong[newItemPosition]
        return oldPerson.id == newPerson.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldPerson = oldSong[oldItemPosition]
        val newPerson = newSong[newItemPosition]
        return oldPerson.title == newPerson.title
    }
}