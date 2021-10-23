package com.example.sportsify

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Environment
import androidx.core.net.toFile
import java.io.*
import java.net.URI
import java.nio.channels.FileChannel


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        launch_camera.setOnClickListener {
            launchCameraActivity()
        }
    }

    private fun launchCameraActivity() {
        Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takeVideoIntent ->
            takeVideoIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takeVideoIntent, 1)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val videoUri: Uri? = intent?.data
        }
    }

    private fun saveFile(uri: Uri?) {
        try {
            val destination: File = File(filesDir.absolutePath + "ab.mp4")
            val source = File(uri?.getPath())
            val src: FileChannel = FileInputStream(source).channel
            val dst: FileChannel = FileOutputStream(destination).channel
            dst.transferFrom(src, 0, src.size())
            src.close()
            dst.close()
        } catch (io: IOException) {
            val i = 0;
        }
    }


}