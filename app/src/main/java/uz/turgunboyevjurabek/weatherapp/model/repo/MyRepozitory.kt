package uz.turgunboyevjurabek.weatherapp.model.repo

import uz.turgunboyevjurabek.weatherapp.model.network.ApiService
import javax.inject.Inject

class MyRepozitory @Inject constructor(private val apiService: ApiService) {
    suspend fun getCurrentWeather(lat:Double,lan:Double)=apiService.getCurrentWeather(lat,lan)

}