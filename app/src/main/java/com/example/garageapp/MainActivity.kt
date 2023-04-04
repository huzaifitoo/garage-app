package com.example.garageapp

import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.garageapp.databinding.ActivityMainBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var carsDatabase: CarsDatabase
    private lateinit var productsApi: ApiMakesInterface
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        carsDatabase = CarsDatabase.getDatabase(applicationContext)


        val adapter = ArrayAdapter<String>(this, R.layout.list_item)
        val autoComplete: AutoCompleteTextView = findViewById(R.id.autoComplete)

        productsApi = MakesRetrofitClient().getClient().create(ApiMakesInterface::class.java)

        productsApi.getAllMakes().enqueue(object : retrofit2.Callback<MakesResponse> {
            override fun onResponse(call: Call<MakesResponse>, response: Response<MakesResponse>) {
                val makesResponse = response.body()

                if (makesResponse != null) {
                    val makesList = makesResponse.results?.map { it?.makeName }
                        ?.filterNotNull() // extract the list of make names from the results and filter out any null values
                    if (makesList != null) {
                        adapter.addAll(makesList)
                    } // add the list of make names to the adapter

                    autoComplete.setAdapter(adapter)

                    autoComplete.onItemClickListener =
                        AdapterView.OnItemClickListener { adapterView, View, i, l ->
                            val itemSelected = adapterView.getItemAtPosition(i)
                            Log.d("Auto", "Selected item: $itemSelected")

                            val makeId = makesResponse.results?.get(i)?.makeID.toString()
                            getModelsByMakeId(makeId)

                            Toast.makeText(
                                this@MainActivity,
                                "item: $itemSelected",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                } else {
                    Log.d("Data", "Response body is null")
                }
            }

            override fun onFailure(call: Call<MakesResponse>, t: Throwable) {
                t.message?.let { Log.d("error something wrong", it) }
            }
        })

        binding.btnAddCar.setOnClickListener {
            Toast.makeText(this@MainActivity, "clicked", Toast.LENGTH_SHORT).show()

            GlobalScope.launch {

                val make = binding.autoComplete.text.toString()
                val model = binding.autoComplete2.text.toString()

                if (make.isNotBlank() && model.isNotBlank()) {
                    val carsData = CarsData(make, model.toInt())
                    carsDatabase.carsDao().insert(carsData)
                    Toast.makeText(this@MainActivity, "Car added successfully!", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Please fill all fields correctly!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }

    }

    private fun getModelsByMakeId(s: String) {

        val adapter = ArrayAdapter<String>(this, R.layout.list_item2)

        productsApi.getModelsByMakeId(s).enqueue(object : Callback<ModelsResponse> {
            override fun onResponse(
                call: Call<ModelsResponse>,
                response: Response<ModelsResponse>
            ) {
                val modelsResponse = response.body()
                if (modelsResponse != null) {

                    val modelsList = modelsResponse.results?.map { it?.modelID }
                        ?.filterNotNull() // extract the list of model IDs from the results and filter out any null values
                    if (modelsList != null) {
                        adapter.addAll(modelsList.map { it.toString() })
                    } // add the list of model IDs to the adapter

                    val autoComplete2: AutoCompleteTextView = findViewById(R.id.autoComplete2)

                    autoComplete2.setAdapter(adapter)

                    autoComplete2.onItemClickListener =
                        AdapterView.OnItemClickListener { adapterView, View, i, l ->
                            val itemSelected = adapterView.getItemAtPosition(i)
                            Log.d("Auto", "Selected item: $itemSelected")

                            Toast.makeText(
                                this@MainActivity,
                                "item: $itemSelected",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    Log.d("Data2", response.body().toString())
                }
            }

            override fun onFailure(call: Call<ModelsResponse>, t: Throwable) {
                t.message?.let { Log.d("error something wrong", it) }
            }

        })
    }
}
