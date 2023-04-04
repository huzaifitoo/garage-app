package com.example.garageapp

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class MakesResponse(

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("Results")
	val results: List<ResultsItem?>? = null,

	@field:SerializedName("Count")
	val count: Int? = null,

	@field:SerializedName("SearchCriteria")
	val searchCriteria: String? = null
) : Parcelable

@Parcelize
data class ResultsItem(

	@field:SerializedName("Make_ID")
	val makeID: Int? = null,

	@field:SerializedName("Make_Name")
	val makeName: String? = null
) : Parcelable
