package com.exam.personalbill.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.exam.personalbill.R

class HomeAdapter(context: Context, private val data: List<String>) :
    ArrayAdapter<String>(context, R.layout.menu_item, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.menu_item, parent, false)
        }

        val textViewCustom = itemView!!.findViewById<TextView>(R.id.listview_textview)
        textViewCustom.text = data[position]

        return itemView
    }
}