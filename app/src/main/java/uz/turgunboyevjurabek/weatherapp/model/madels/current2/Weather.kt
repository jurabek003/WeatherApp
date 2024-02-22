package uz.turgunboyevjurabek.weatherapp.model.madels.current2


import com.google.gson.annotations.SerializedName

 class Weather {
     val description: String? = null
     val icon: String? = null
     val id: Int? = null
     val main: String? = null
     override fun toString(): String {
         return "Weather(description=$description, icon=$icon, id=$id, main=$main)"
     }

 }