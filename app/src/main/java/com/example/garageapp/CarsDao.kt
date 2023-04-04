package com.example.garageapp

import androidx.room.*

@Dao
interface CarsDao {

    @Insert
    suspend fun insert(carsData: CarsData)

    @Delete
    suspend fun delete(carsData: CarsData)

    @Query("Select * From Cars_Table")
    suspend fun getContact():List<CarsData>
}