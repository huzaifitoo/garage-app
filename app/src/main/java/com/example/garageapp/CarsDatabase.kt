package com.example.garageapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CarsData::class], version = 1)
abstract class CarsDatabase : RoomDatabase() {

    abstract fun carsDao(): CarsDao

    companion object {
        private var INSTANCE: CarsDatabase? = null
        fun getDatabase(context: Context): CarsDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE =
                        Room.databaseBuilder(context,CarsDatabase::class.java, "cars_db")
                            .build()
                }
            }
            return INSTANCE!!
        }
    }
}
