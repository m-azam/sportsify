package com.example.sportsify

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


class CustomAdapter(applicationContext: Context, logos: IntArray) :
    BaseAdapter() {
    var context: Context
    var logos: IntArray
    var inflter: LayoutInflater

    override fun getCount(): Int {
        return logos.size
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup?): View {
        val view1 = inflter.inflate(R.layout.item, null) // inflate the layout
        val icon: ImageView =
            view1.findViewById(R.id.icon) as ImageView // get the reference of ImageView
        icon.setImageResource(logos[i]) // set logo images
        return view1
    }

    init {
        context = applicationContext
        this.logos = logos
        inflter = LayoutInflater.from(applicationContext)
    }
}