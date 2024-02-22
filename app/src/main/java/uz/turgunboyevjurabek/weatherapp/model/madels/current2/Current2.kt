package uz.turgunboyevjurabek.weatherapp.model.madels.current2


import com.google.gson.annotations.SerializedName

 class Current2 {
     val base: String?=null
     val clouds: Clouds?=null
     val cod: Int?=null
     val coord: Coord?=null
     val dt: Int?=null
     val id: Int?=null
     val main: Main?=null
     val name: String?=null
     val sys: Sys?=null
     val timezone: Int?=null
     val visibility: Int?=null
     val weather: List<Weather>?=null
     val wind: Wind?=null
     override fun toString(): String {
         return "Current2(base=$base, clouds=$clouds, cod=$cod, coord=$coord, dt=$dt, id=$id, main=$main, name=$name, sys=$sys, timezone=$timezone, visibility=$visibility, weather=$weather, wind=$wind)"
     }

 }