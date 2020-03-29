package com.example.temperatureappr.locationsetup

import androidx.lifecycle.MutableLiveData
import com.example.temperatureappr.base.BaseViewModel
import com.example.temperatureappr.data.UseCaseResult
import com.example.temperatureappr.data.model.LocationWithWeather
import com.example.temperatureappr.data.repository.OutsideTemperatureRepository
import com.example.temperatureappr.utils.LocationHelper
import com.example.temperatureappr.utils.SharedPrefs
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LocationSetupViewModel(
    val locationHelper: LocationHelper,
    private val outsideTemperatureRepository: OutsideTemperatureRepository
) : BaseViewModel() {

    val location = MutableLiveData<LocationWithWeather>()

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            if (locationResult.lastLocation != null) {
                locationHelper.stopLocationUpdates(this)
                launch {
                    when (val result =
                        withContext(Dispatchers.IO) {
                            outsideTemperatureRepository.getTemperatureLatLon(
                                locationResult.lastLocation.latitude,
                                locationResult.lastLocation.longitude
                            )
                        }) {
                        is UseCaseResult.Success -> location.value = result.data
                        is UseCaseResult.Error -> showError.value = result.exception.message
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        locationHelper.stopLocationUpdates(locationCallback)
    }
}