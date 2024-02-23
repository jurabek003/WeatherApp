package uz.turgunboyevjurabek.weatherapp.model.madels.hourly


import com.google.gson.annotations.SerializedName

data class ApiHourly(
    @SerializedName("city")
    val city: City?=null,
    @SerializedName("cnt")
    val cnt: Int?=null,
    @SerializedName("cod")
    val cod: String?=null,
    @SerializedName("list")
    val list: ArrayList<Hourly>?=null,
    @SerializedName("message")
    val message: Int?=null
)