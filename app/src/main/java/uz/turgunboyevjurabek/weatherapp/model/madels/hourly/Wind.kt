package uz.turgunboyevjurabek.weatherapp.model.madels.hourly


import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("deg")
    val deg: Int?=null,
    @SerializedName("gust")
    val gust: Double?=null,
    @SerializedName("speed")
    val speed: Double?=null
)