package com.exam.personalbill.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import com.exam.personalbill.Database
import com.exam.personalbill.DepositEvery
import com.exam.personalbill.R
import com.exam.personalbill.activity.AuditingActivity
import com.exam.personalbill.activity.EditAuditActivity

class AuditingAdapter(private val context: Context, private val datasource: List<DepositEvery>,private val database: Database) :
    BaseAdapter() {
    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return datasource.size
    }

    override fun getItem(position: Int): Any {
        return datasource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertview: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder
        if (convertview == null) {
            view = inflater.inflate(R.layout.auditing_item, parent, false)
            holder = ViewHolder()
            holder.catagoryTextView = view.findViewById(R.id.categoryTextView)
            holder.amountTextView = view.findViewById(R.id.amountTextView)
            holder.timestampTextView = view.findViewById(R.id.timestampTextView)
            holder.editbutton = view.findViewById(R.id.buttonEdit)
            holder.deletebutton = view.findViewById(R.id.buttonDelete)

            view.tag = holder
        } else {
            view = convertview
            holder = view.tag as ViewHolder
        }

        val deposit = getItem(position) as DepositEvery
        holder.catagoryTextView.text = deposit.category
        holder.amountTextView.text = deposit.amount.toString()
        holder.timestampTextView.text = deposit.timestamp
        holder.editbutton.setOnClickListener {
            val intent = Intent(context, EditAuditActivity::class.java)
            intent.putExtra("DEPOSIT_ID", deposit.id)
            context.startActivity(intent)
        }
        holder.deletebutton.setOnClickListener {
            database.deleteDeposit(deposit.id)
            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
            (context as AuditingActivity).loadDeposits() // Refresh the list
        }
        return view
    }

    private class ViewHolder {
        lateinit var catagoryTextView: TextView
        lateinit var amountTextView: TextView
        lateinit var timestampTextView: TextView
        lateinit var editbutton: Button
        lateinit var deletebutton:Button
    }
}