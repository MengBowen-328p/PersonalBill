package com.exam.personalbill.fragment

import android.os.Bundle
import android.content.Intent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import com.exam.personalbill.Database
import com.exam.personalbill.R
import android.widget.ArrayAdapter
import com.exam.personalbill.activity.AccountingActivity
import com.exam.personalbill.activity.AuditingActivity
import com.exam.personalbill.adapter.HomeAdapter


/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {

    private lateinit var listView: ListView

    private lateinit var db: Database
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        listView = view.findViewById(R.id.list)
//        listView = view.findViewById(R.id.menu_list)
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = Database(requireContext())
        val data = listOf("Accounting", "Auditing")
        val imageIds = listOf(
            R.mipmap.mainicon,
            R.mipmap.mainicon
        )
        var listView = view.findViewById<ListView>(R.id.list)
        val adapter = HomeAdapter(requireContext(), R.layout.menu_item, data, imageIds)
        listView.adapter = adapter

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                // 启动新的 Activity 并传递数据
                val selecteditemtext = data[position]
                if (selecteditemtext == "Accounting") {
                    // 跳转到第一个 Activity
                    val intent = Intent(activity, AccountingActivity::class.java)
                    intent.putExtra("ITEM_TEXT", selecteditemtext)
                    intent.putExtra("ITEM_IMAGE", imageIds[position])
                    startActivity(intent)
                } else if (selecteditemtext == "Auditing") {
                    // 跳转到第二个 Activity
                    val intent = Intent(activity, AuditingActivity::class.java)
                    intent.putExtra("ITEM_TEXT", selecteditemtext)
                    intent.putExtra("ITEM_IMAGE", imageIds[position])
                    startActivity(intent)
                }
            }
    }
}
