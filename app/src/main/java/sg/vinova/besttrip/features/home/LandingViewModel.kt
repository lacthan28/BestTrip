package sg.vinova.besttrip.features.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.location.Location
import sg.vinova.besttrip.repositories.LocationRepository
import sg.vinova.besttrip.repositories.livedata.AbsentLiveData

class LandingViewModel(repo: LocationRepository) : ViewModel() {
    private val location = MutableLiveData<Location?>()
    fun setLocation(value: Location) {
        location.value = value
    }

    val getAddress: LiveData<String>

    init {
        getAddress = Transformations.switchMap<Location?, String>(location, {
            if (it == null) {
                AbsentLiveData.create()
            } else {
                repo.getAddressByLocation(it)
            }
        })
    }
}