package com.exam.personalbill.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.drawerlayout.R

class HomeAdapter(context: Context, private val resource: Int, private val items: List<String>) :
    ArrayAdapter<String>(context, resource, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)

        val viewHolder: ViewHolder
        if (view.tag == null) {
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }

        viewHolder.listView.adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, items)

        return view
    }

    class ViewHolder(view: View) {
        val listView: ListView = view.findViewById(R.id.menu)
    }
}