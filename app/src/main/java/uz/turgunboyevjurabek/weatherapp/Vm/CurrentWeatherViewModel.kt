package uz.turgunboyevjurabek.weatherapp.Vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import uz.turgunboyevjurabek.weatherapp.model.repo.MyRepozitory
import uz.turgunboyevjurabek.weatherapp.model.madels.current2.Current2
import uz.turgunboyevjurabek.weatherapp.utils.Resource
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val myRepozitory: MyRepozitory
):ViewModel() {
    private val liveData=MutableLiveData<Resource<Current2>>()
    fun getCurrentWeather(lat:Double,lon:Double):MutableLiveData<Resource<Current2>>{
        liveData.postValue(Resource.loading("Loading at CurrentWeatherViewModel"))
        viewModelScope.launch {
            try {
                val getData=async {
                    myRepozitory.getCurrentWeather(lat,lon)
                }.await()
                liveData.postValue(Resource.success(getData))
            }catch (e:Exception){
                liveData.postValue(Resource.error(e.message))
            }
        }

        return liveData
    }
}