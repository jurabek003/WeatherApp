package uz.turgunboyevjurabek.weatherapp.model.madels.hourly


import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("1h")
    val h: Double
)