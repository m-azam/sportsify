package com.example.sportsify

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val intent = intent
        if (intent.getIntExtra("position", -1) == 0) {
            Glide.with(this)
                .load(intent.getStringExtra("gifUrl"))
                .signature(ObjectKey(System.currentTimeMillis()))
                .into(selectedImage)

        } else {
            selectedImage.setImageResource(intent.getIntExtra("image", 0))
        }
    }

}