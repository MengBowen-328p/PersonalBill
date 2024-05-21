package com.exam.personalbill.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.exam.personalbill.Database
import com.exam.personalbill.R
import android.widget.ArrayAdapter
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
        val view = inflater.inflate(R.layout.fragment_home,container,false)
        listView = view.findViewById(R.id.list)
//        listView = view.findViewById(R.id.menu_list)
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = Database(requireContext())
        val data = listOf("Accounting","Auditing")
        var listView = view.findViewById<ListView>(R.id.list)
        val adapter = HomeAdapter(requireContext(),data)
        listView.adapter = adapter
    }

}