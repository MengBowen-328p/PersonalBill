package com.exam.personalbill.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.exam.personalbill.Database
import com.exam.personalbill.R
import com.exam.personalbill.adapter.AuditingAdapter

class AuditingActivity : AppCompatActivity() {

    private lateinit var listviewDeposit: ListView
    private lateinit var database: Database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auditing)

        listviewDeposit = findViewById(R.id.auditing_listview)
        database = Database(this)

        loadDeposits()
    }
    fun loadDeposits()
    {
        val deposits = database.getEveryDeposits()
        val adapter = AuditingAdapter(this,deposits,database)
        listviewDeposit.adapter = adapter
    }
}