package com.exam.personalbill.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.exam.personalbill.R
import com.exam.personalbill.Database

class EditAuditActivity : AppCompatActivity() {

    private lateinit var database:Database
    private lateinit var editTextCategory: EditText
    private lateinit var editTextAmount: EditText
    private lateinit var buttonUpdate: Button
    private var depositId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_audit)

        database = Database(this)
        editTextCategory = findViewById(R.id.editTextCategory)
        editTextAmount = findViewById(R.id.editTextAmount)
        buttonUpdate = findViewById(R.id.buttonUpdate)

        depositId = intent.getIntExtra("DEPOSIT_ID", -1)
        loadDepositDetails(depositId)

        buttonUpdate.setOnClickListener {
            val category = editTextCategory.text.toString()
            val amount = editTextAmount.text.toString().toDouble()
            database.updateDeposit(depositId, category, amount)
            finish()

        }
    }

    private fun loadDepositDetails(depositId: Int) {
        val deposit = database.getDepositById(depositId)
        if (deposit != null) {
            editTextCategory.setText(deposit.category)
            editTextAmount.setText(deposit.amount.toString())
        }
    }
}
