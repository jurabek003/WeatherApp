package uz.turgunboyevjurabek.weatherapp.model.madels.hourly


import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int?=null
)