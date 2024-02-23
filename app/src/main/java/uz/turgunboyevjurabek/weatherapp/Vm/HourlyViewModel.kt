package uz.turgunboyevjurabek.weatherapp.Vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import uz.turgunboyevjurabek.weatherapp.model.madels.hourly.ApiHourly
import uz.turgunboyevjurabek.weatherapp.model.repo.MyRepozitory
import uz.turgunboyevjurabek.weatherapp.utils.Resource
import javax.inject.Inject

@HiltViewModel
class HourlyViewModel @Inject constructor(private val myRepozitory: MyRepozitory) :ViewModel() {
    private val getApiLiveData= MutableLiveData<Resource<ApiHourly>>()
    fun getHourlyData(lat:Double,lon:Double):MutableLiveData<Resource<ApiHourly>>{
        viewModelScope.launch {
            try {
                getApiLiveData.postValue(Resource.loading("Loading at hourly ViewModel"))
                    val data=async {
                        myRepozitory.getMyHourlyWeather(lat,lon)
                    }.await()
                    getApiLiveData.postValue(Resource.success(data))

            }catch (e:Exception){
                getApiLiveData.postValue(Resource.error(e.message))
            }
        }
        return getApiLiveData
    }


}