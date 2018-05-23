package sg.vinova.besttrip.features.searchlocation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import sg.vinova.besttrip.repositories.LocationRepository

class SearchViewModel(repo: LocationRepository) : ViewModel() {
    private var searchText = MutableLiveData<String>()
    fun setSearchText(value: String) {
        searchText.value = value
    }

    val locationList: LiveData<String>

    init {
        locationList = Transformations.switchMap<String, String>(searchText, {
            if (it.isEmpty()) {
                repo.getNearbyList()
            } else {
                repo.getLocationList(it)
            }
        })
    }
}