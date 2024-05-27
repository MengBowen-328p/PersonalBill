package com.exam.personalbill

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor

class Database(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "PersonalBill.db"     //declare database name
        private const val DATABASE_VERSION = 1

        private const val USER_TABLE_NAME = "Users"             //declare USER_TABLE Para
        private const val USER_COLUMN_ID = "id"
        private const val USER_COLUMN_USERNAME = "username"
        private const val USER_COLUMN_PASSWORD = "password"

        private const val ACCOUNT_TABLE_NAME = "deposits"       //declare ACCOUNT_TABLE Para
        private const val ACCOUNT_COLUMN_ID = "id"
        private const val ACCOUNT_COLUMN_CATEGORY = "category"
        private const val ACCOUNT_COLUMN_AMOUNT = "amount"
        private const val ACCOUNT_COLUMN_TIMESTAMP = "timestamp"
    }

    override fun onCreate(db: SQLiteDatabase) {                             //override class SQLiteDatabase
        val createUsersTable = ("CREATE TABLE $USER_TABLE_NAME ("           //create USER_TABLE
                + "$USER_COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$USER_COLUMN_USERNAME TEXT,"
                + "$USER_COLUMN_PASSWORD TEXT)")
        db.execSQL(createUsersTable)

        val createAccountTable = ("CREATE TABLE $ACCOUNT_TABLE_NAME("       //create ACCOUNT_TABLE
                + "$ACCOUNT_COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$ACCOUNT_COLUMN_CATEGORY TEXT,"
                + "$ACCOUNT_COLUMN_AMOUNT REAL,"
                + "$ACCOUNT_COLUMN_TIMESTAMP TEXT)")
        db.execSQL(createAccountTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $USER_TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS $ACCOUNT_TABLE_NAME")
        onCreate(db)
    }

    fun addUser(username: String, password: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(USER_COLUMN_USERNAME, username)
        values.put(USER_COLUMN_PASSWORD, password)

        return db.insert(USER_TABLE_NAME, null, values)
    }

    fun getUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val query =
            "SELECT * FROM $USER_TABLE_NAME WHERE $USER_COLUMN_USERNAME = ? AND $USER_COLUMN_PASSWORD = ?"
        val cursor = db.rawQuery(query, arrayOf(username, password))
        val count = cursor.count
        cursor.close()
        return count > 0
    }

    fun isUserLoggedIn(): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $USER_TABLE_NAME", null)
        val isLoggedIn = cursor.count > 0
        cursor.close()
        db.close()
        return isLoggedIn
    }

    fun clearUserData() {
        val db = this.writableDatabase
        db.delete(USER_TABLE_NAME, null, null)
        db.close()
    }

    fun checkUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM $USER_TABLE_NAME WHERE $USER_COLUMN_USERNAME = ? AND $USER_COLUMN_PASSWORD = ?",
            arrayOf(username, password)
        )
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    fun addAccount(category: String, amount: Float, timestamp: Float): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ACCOUNT_COLUMN_CATEGORY, category)
        values.put(ACCOUNT_COLUMN_AMOUNT, amount)
        values.put(ACCOUNT_COLUMN_TIMESTAMP, timestamp)

        return db.insert(ACCOUNT_TABLE_NAME, null, values)
    }

    @SuppressLint("Range")
    fun getAllDeposits(): List<DepositAll> {
        val deposits = mutableListOf<DepositAll>()
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT $ACCOUNT_COLUMN_CATEGORY, SUM($ACCOUNT_COLUMN_AMOUNT) as total_amount FROM $ACCOUNT_TABLE_NAME GROUP BY $ACCOUNT_COLUMN_CATEGORY",
            null
        )
        if (cursor.moveToFirst()) {
            do {
                val category =
                    cursor.getString(cursor.getColumnIndexOrThrow(ACCOUNT_COLUMN_CATEGORY))
                val totalAmount = cursor.getDouble(cursor.getColumnIndexOrThrow("total_amount"))
                deposits.add(DepositAll(0, category, totalAmount))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return deposits
    }

    fun getEveryDeposits(): List<DepositEvery> {
        val deposits = mutableListOf<DepositEvery>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $ACCOUNT_TABLE_NAME", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(ACCOUNT_COLUMN_ID))
                val category =
                    cursor.getString(cursor.getColumnIndexOrThrow(ACCOUNT_COLUMN_CATEGORY))
                val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(ACCOUNT_COLUMN_AMOUNT))
                val timestamp = cursor.getString(
                    cursor.getColumnIndexOrThrow(
                        ACCOUNT_COLUMN_TIMESTAMP
                    )
                )
                deposits.add(DepositEvery(id, category, amount, timestamp))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return deposits
    }

    fun insertDeposit(category: String, amount: Double, timestamp: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ACCOUNT_COLUMN_CATEGORY, category)
        contentValues.put(ACCOUNT_COLUMN_AMOUNT, amount)
        contentValues.put(ACCOUNT_COLUMN_TIMESTAMP, timestamp)
        db.insert(ACCOUNT_TABLE_NAME, null, contentValues)
    }

    fun deleteDeposit(id: Int) {
        val db = this.writableDatabase
        db.delete(ACCOUNT_TABLE_NAME, "$ACCOUNT_COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }

    fun updateDeposit(id: Int, category: String, amount: Double) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(ACCOUNT_COLUMN_CATEGORY, category)
            put(ACCOUNT_COLUMN_AMOUNT, amount)
        }
        db.update(ACCOUNT_TABLE_NAME, values, "$ACCOUNT_COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }

    fun getDepositById(id: Int): DepositEvery {
        val db = this.readableDatabase
        val cursor = db.query(ACCOUNT_TABLE_NAME, null, "$ACCOUNT_COLUMN_ID=?", arrayOf(id.toString()), null, null, null)
        cursor?.moveToFirst()
        val deposit = DepositEvery(
            cursor!!.getInt(cursor.getColumnIndexOrThrow(ACCOUNT_COLUMN_ID)),
            cursor.getString(cursor.getColumnIndexOrThrow(ACCOUNT_COLUMN_CATEGORY)),
            cursor.getDouble(cursor.getColumnIndexOrThrow(ACCOUNT_COLUMN_AMOUNT)),
            cursor.getString(cursor.getColumnIndexOrThrow(ACCOUNT_COLUMN_TIMESTAMP))
        )
        cursor.close()
        return deposit
    }
}