package com.example.garageapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MakesRetrofitClient {
    private lateinit var retrofit: Retrofit

    fun getClient(): Retrofit {
        retrofit = Retrofit
            .Builder()
            .baseUrl("https://vpic.nhtsa.dot.gov/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
    }
}

//https://vpic.nhtsa.dot.gov/api/vehicles/getallmakes?format=json