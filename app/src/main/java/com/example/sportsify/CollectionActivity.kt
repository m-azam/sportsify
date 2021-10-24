package com.example.sportsify

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_collection.*
import android.content.Intent




class CollectionActivity : AppCompatActivity() {

    val images: IntArray = intArrayOf(R.drawable.one, R.drawable.two,
        R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.one,
    R.drawable.two, R.drawable.three, R.drawable.one, R.drawable.two, R.drawable.four,
    R.drawable.five, R.drawable.one, R.drawable.two, R.drawable.two, R.drawable.one, R.drawable.two,
        R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.one,
        R.drawable.two, R.drawable.three, R.drawable.one, R.drawable.two, R.drawable.four,
        R.drawable.five, R.drawable.one, R.drawable.two, R.drawable.two)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)
        val picUrl: String = intent.extras?.get("picUrl") as String
        val gifUrl: String = intent.extras?.get("gifUrl") as String
        val customAdapter = CustomAdapter(applicationContext, images, picUrl)
        simple_grid_view.setAdapter(customAdapter)
        simple_grid_view.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("position", 0)
            intent.putExtra("gifUrl", gifUrl)
            intent.putExtra("image", images[position]) // put image data in Intent
            startActivity(intent)
        }
    }

}