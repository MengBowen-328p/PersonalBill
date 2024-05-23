package com.exam.personalbill.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.exam.personalbill.Database
import java.text.SimpleDateFormat
import java.util.*
import com.exam.personalbill.R


class AccountingActivity : AppCompatActivity() {

    private lateinit var editTextCategory: EditText
    private lateinit var editTextAmount: EditText
    private lateinit var editTextTimestamp: EditText
    private lateinit var buttonSave: Button
    private lateinit var database: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accounting)
        editTextAmount = findViewById(R.id.editTextAmount)
        editTextCategory = findViewById(R.id.editTextCategory)
        editTextTimestamp = findViewById(R.id.editTextTimestamp)
        buttonSave = findViewById(R.id.buttonSave)

        database = Database(this)

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        editTextTimestamp.setText(sdf.format(Date()))

        showDateTimePicker()


        buttonSave.setOnClickListener {

            saveDeposit()
        }
    }

    private fun showDateTimePicker() {
        val currentDate = Calendar.getInstance()
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                currentDate.set(Calendar.YEAR, year)
                currentDate.set(Calendar.MONTH, monthOfYear)
                currentDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                TimePickerDialog(
                    this,
                    TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                        currentDate.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        currentDate.set(Calendar.MINUTE, minute)

                        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                        editTextTimestamp.setText(sdf.format(currentDate.time))
                    },
                    currentDate.get(Calendar.HOUR_OF_DAY),
                    currentDate.get(Calendar.MINUTE),
                    true
                ).show()
            }

        DatePickerDialog(
            this,
            dateSetListener,
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun saveDeposit() {
        val category = editTextCategory.text.toString()
        val amount = editTextAmount.text.toString().toDoubleOrNull()
        val timestamp = editTextTimestamp.text.toString()

        if (category.isNotEmpty() && amount != null) {
            database.insertDeposit(category, amount, timestamp)
            finish()
        } else {
            // Handle error: Show a message to the user
        }
    }
}