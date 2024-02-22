package uz.turgunboyevjurabek.weatherapp.model.madels.current2


import com.google.gson.annotations.SerializedName

 class Main{
    val feels_like: Double?=null
    val grnd_level: Int?=null
    val humidity: Int?=null
    val pressure: Int?=null
    val sea_level: Int?=null
    val temp: Double?=null
    val temp_max: Double?=null
    val temp_min: Double?=null
    override fun toString(): String {
       return "Main(feels_like=$feels_like, grnd_level=$grnd_level, humidity=$humidity, pressure=$pressure, sea_level=$sea_level, temp=$temp, temp_max=$temp_max, temp_min=$temp_min)"
    }

 }