package uz.turgunboyevjurabek.weatherapp.model.madels.current2


import com.google.gson.annotations.SerializedName

 class Sys{
    val country: String?=null
    val sunrise: Int?=null
    val sunset: Int?=null
     override fun toString(): String {
         return "Sys(country=$country, sunrise=$sunrise, sunset=$sunset)"
     }

 }