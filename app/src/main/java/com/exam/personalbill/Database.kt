package com.exam.personalbill

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor

class Database(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,DATABASE_VERSION) {
//    companion object{
//        private const val DATABASE_VERSION = 1
//        private const val DATABASE_NAME = "PersonalBill.db"
//        private const val TABLE_USERS = "Users"
//        private const val COLUMN_ID = "id"
//        private const val COLUMN_USERNAME = "username"
//        private const val COLUMN_PASSWORD = "password"
//    }
    companion object {
        private const val DATABASE_NAME = "PersonalBill.db"     //declare database name
        private const val DATABASE_VERSION = 1

        private const val USER_TABLE_NAME = "Users"
        private const val USER_COLUMN_ID = "id"
        private const val USER_COLUMN_USERNAME = "username"
        private const val USER_COLUMN_PASSWORD = "password"

        const val TABLE_NAME = "deposits"
        const val COLUMN_ID = "id"
        const val COLUMN_CATEGORY = "category"
        const val COLUMN_AMOUNT = "amount"
    }
    override fun onCreate(db: SQLiteDatabase) {
        val createUsersTable = ("CREATE TABLE $USER_TABLE_NAME ("
                + "$USER_COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$USER_COLUMN_USERNAME TEXT,"
                + "$USER_COLUMN_PASSWORD TEXT)")
        db.execSQL(createUsersTable)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $USER_TABLE_NAME")
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
        val query = "SELECT * FROM $USER_TABLE_NAME WHERE $USER_COLUMN_USERNAME = ? AND $USER_COLUMN_PASSWORD = ?"
        val cursor = db.rawQuery(query, arrayOf(username, password))
        val count = cursor.count
        cursor.close()
        return count > 0
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

    @SuppressLint("Range")
    fun getAllDeposits(): List<Deposit> {
        val deposits = mutableListOf<Deposit>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                val category = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY))
                val amount = cursor.getDouble(cursor.getColumnIndex(COLUMN_AMOUNT))
                deposits.add(Deposit(id, category, amount))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return deposits
    }
}