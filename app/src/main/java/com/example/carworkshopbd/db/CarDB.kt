package com.example.carworkshopbd.db

import android.content.Context
import androidx.room.Room

object CarDB {

    private var database: CarWorkshopDB? = null

    fun getDatabase(context: Context): CarWorkshopDB? {

        return if (database != null) {
            database
        } else {
            database = Room.databaseBuilder(
                context.applicationContext,
                CarWorkshopDB::class.java,
                "car.db"
            ).build()
            database
        }
    }
}