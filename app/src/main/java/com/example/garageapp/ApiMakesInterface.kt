package com.example.garageapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiMakesInterface {
    @GET("vehicles/getallmakes?format=json")
    fun getAllMakes(): Call<MakesResponse>

    @GET("vehicles/GetModelsForMakeId/{make_id}?format=json")
    fun getModelsByMakeId(@Path("make_id") make_id:  String): Call<ModelsResponse>

}
//11897
