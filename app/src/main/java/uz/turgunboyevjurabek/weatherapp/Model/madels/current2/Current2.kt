package uz.turgunboyevjurabek.weatherapp.model.madels.current2


import com.google.gson.annotations.SerializedName

data class Current2(
    val base: String,
    val clouds: uz.turgunboyevjurabek.weatherapp.model.madels.current2.Clouds,
    val cod: Int,
    val coord: uz.turgunboyevjurabek.weatherapp.model.madels.current2.Coord,
    val dt: Int,
    val id: Int,
    val main: uz.turgunboyevjurabek.weatherapp.model.madels.current2.Main,
    val name: String,
    val sys: uz.turgunboyevjurabek.weatherapp.model.madels.current2.Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<uz.turgunboyevjurabek.weatherapp.model.madels.current2.Weather>,
    val wind: uz.turgunboyevjurabek.weatherapp.model.madels.current2.Wind
)