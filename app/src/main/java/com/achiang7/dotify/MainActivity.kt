package com.achiang7.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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


}
