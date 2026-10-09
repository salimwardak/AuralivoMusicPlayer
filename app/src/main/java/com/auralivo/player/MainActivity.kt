
package com.auralivo.player

import android.Manifest
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.graphics.Color
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {

    private var player: MediaPlayer? = null
    private val songs = mutableListOf<Pair<String, Uri>>()
    private var currentIndex = 0
    private lateinit var songTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(32, 32, 32, 32)
            setBackgroundColor(Color.rgb(8, 14, 28))
        }

        fun button(title: String, action: () -> Unit) {
            val b = Button(this)
            b.text = title
            b.setOnClickListener { action() }
            layout.addView(b)
        }

        val heading = TextView(this).apply {
            text = "AURALIVO MUSIC PLAYER"
            textSize = 25f
            setTextColor(Color.CYAN)
            gravity = Gravity.CENTER
        }
        layout.addView(heading)

        songTitle = TextView(this).apply {
            text = "Your Music. Your Atmosphere."
            textSize = 18f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            setPadding(0, 40, 0, 40)
        }
        layout.addView(songTitle)

        button("PLAY / PAUSE") {
            val p = player
            if (p == null) {
                playSong()
            } else if (p.isPlaying) {
                p.pause()
            } else {
                p.start()
            }
        }

        button("NEXT") {
            if (songs.isNotEmpty()) {
                currentIndex = (currentIndex + 1) % songs.size
                playSong()
            }
        }

        button("PREVIOUS") {
            if (songs.isNotEmpty()) {
                currentIndex =
                    (currentIndex - 1 + songs.size) % songs.size
                playSong()
            }
        }

        button("SCAN MUSIC") {
            loadSongs()
        }

        setContentView(layout)
        requestAudioPermission()
    }

    private fun requestAudioPermission() {
        val permission =
            if (Build.VERSION.SDK_INT >= 33)
                Manifest.permission.READ_MEDIA_AUDIO
            else
                Manifest.permission.READ_EXTERNAL_STORAGE

        if (checkSelfPermission(permission) ==
            PackageManager.PERMISSION_GRANTED) {
            loadSongs()
        } else {
            requestPermissions(arrayOf(permission), 100)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(
            requestCode, permissions, grantResults
        )

        if (requestCode == 100 &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadSongs()
        }
    }

    private fun loadSongs() {
        songs.clear()

        val collection =
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE
        )

        contentResolver.query(
            collection, projection, null, null,
            "${MediaStore.Audio.Media.TITLE} ASC"
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(
                MediaStore.Audio.Media._ID
            )
            val titleColumn = cursor.getColumnIndexOrThrow(
                MediaStore.Audio.Media.TITLE
            )

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val title = cursor.getString(titleColumn)
                val uri = Uri.withAppendedPath(
                    collection, id.toString()
                )
                songs.add(title to uri)
            }
        }

        songTitle.text = "${songs.size} songs found"
    }

    private fun playSong() {
        if (songs.isEmpty()) {
            songTitle.text = "No music found"
            return
        }

        player?.release()
        player = null

        try {
            val song = songs[currentIndex]
            player = MediaPlayer.create(this, song.second)
            player?.setOnCompletionListener {
                currentIndex = (currentIndex + 1) % songs.size
                playSong()
            }
            player?.start()
            songTitle.text = song.first
        } catch (e: Exception) {
            songTitle.text = "Unable to play this file"
        }
    }

    override fun onDestroy() {
        player?.release()
        player = null
        super.onDestroy()
    }
}
