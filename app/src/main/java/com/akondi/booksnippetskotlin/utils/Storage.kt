package com.akondi.booksnippetskotlin.utils

import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.akondi.booksnippetskotlin.mvp.model.Car
import java.util.ArrayList
import javax.inject.Inject

open class Storage @Inject
constructor(context: Context) : SQLiteOpenHelper(context, "cars_db", null, 1) {

    val savedCars: List<Car>
        get() {
            val carList = ArrayList<Car>()
            val db = this.writableDatabase

            try {
                val cursor = db.rawQuery(SELECT_QUERY, null)
                if (cursor != null) {
                    if (cursor.count > 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                val car = Car()
                                car.name = cursor.getString(cursor.getColumnIndex(NAME))
                                car.vin = cursor.getString(cursor.getColumnIndex(VIN))
                                carList.add(car)
                            } while (cursor.moveToNext())
                        }
                    }
                    cursor.close()
                }
            } catch (e: SQLException) {
                Log.d(TAG, e.message)
            }
            return carList
        }

    override fun onCreate(db: SQLiteDatabase) {
        try {
            db.execSQL(CREATE_TABLE)
        } catch (e: SQLException) {
            Log.d(TAG, e.message)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun addCar(car: Car) {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(NAME, car.name)
        values.put(VIN, car.vin)

        try {
            db.insert(TABLE_NAME, null, values)
        } catch (e: SQLException) {
            Log.d(TAG, e.message)
        }
        db.close()
    }

    companion object {

        private val TAG = Storage::class.java.simpleName

        private const val ID = "id"
        private const val NAME = "name"
        private const val VIN = "vin"
        private const val TABLE_NAME = "cars"
        private const val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        private const val SELECT_QUERY = "SELECT * FROM $TABLE_NAME"

        private const val CREATE_TABLE = "create table " + TABLE_NAME + "(" +
                ID + " integer primary key autoincrement not null," +
                NAME + " text not null," +
                VIN + " text not null)"
    }
}