package com.exam.personalbill.adapter

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.exam.personalbill.R

class HomeAdapter(
    context: Context,
    private val resource: Int,
    private val items: List<String>,
    private val imageIds: List<Int>
) : ArrayAdapter<String>(context, resource, items) {

    override fun getCount(): Int {
        return items.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource,parent,false)
        val viewHolder: ViewHolder
        if(view.tag == null){
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            viewHolder = view.tag as ViewHolder
        }
        viewHolder.titleTextView.text = items[position]

        viewHolder.itemImageView.setImageResource(imageIds[position])
        return view
    }
    class ViewHolder(view: View) {
        val titleTextView: TextView = view.findViewById(R.id.listview_textview)
        val itemImageView: ImageView = view.findViewById(R.id.listview_imageview)
    }
}