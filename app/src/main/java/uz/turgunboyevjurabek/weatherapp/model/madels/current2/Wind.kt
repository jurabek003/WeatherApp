package uz.turgunboyevjurabek.weatherapp.model.madels.current2


import com.google.gson.annotations.SerializedName

 class Wind{
    val deg: Int?=null
    val gust: Double?=null
    val speed: Double?=null
     override fun toString(): String {
         return "Wind(deg=$deg, gust=$gust, speed=$speed)"
     }

 }