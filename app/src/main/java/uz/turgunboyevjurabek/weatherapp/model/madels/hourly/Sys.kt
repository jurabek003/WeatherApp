package uz.turgunboyevjurabek.weatherapp.model.madels.hourly


import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("pod")
    val pod: String?=null
)