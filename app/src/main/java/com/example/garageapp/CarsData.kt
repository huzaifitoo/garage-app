package com.example.garageapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cars_Table")
data class CarsData(
    @PrimaryKey val carName: String,
    val carModel: Int
)

