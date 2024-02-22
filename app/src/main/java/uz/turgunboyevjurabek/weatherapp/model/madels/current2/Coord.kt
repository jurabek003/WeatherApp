package uz.turgunboyevjurabek.weatherapp.model.madels.current2


import com.google.gson.annotations.SerializedName

 class Coord{
    val lat: Double?=null
    val lon: Double?=null
     override fun toString(): String {
         return "Coord(lat=$lat, lon=$lon)"
     }

 }