package com.example.shop.logic

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "my_database"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "persons"
        private const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_SURNAME = "surname"
        const val COLUMN_EMAIL = "email"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NAME TEXT, $COLUMN_SURNAME TEXT,$COLUMN_EMAIL TEXT)"
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun insertData(name: String, surname:String,email:String): Long {
        // Insert data into the database
        val values = ContentValues()
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_SURNAME,surname)
        values.put(COLUMN_EMAIL,email)
        return writableDatabase.insert(TABLE_NAME, null, values)
    }

    fun getAllData(): Cursor {
        return readableDatabase.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    fun deleteData(id: Long): Int {
        return writableDatabase.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(id.toString()))
    }
}