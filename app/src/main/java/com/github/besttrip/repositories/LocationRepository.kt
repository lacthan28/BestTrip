package com.github.besttrip.repositories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.location.Geocoder
import android.location.Location
import com.github.besttrip.BesttripApp
import java.util.*

interface LocationRepository {
    fun getAddressByLocation(location: Location): LiveData<String>
    fun getNearbyList(): LiveData<String>
    fun getLocationList(searchText: String): LiveData<String>
}

class LocationRepositoryImpl : LocationRepository {
    override fun getAddressByLocation(location: Location): LiveData<String> {
        val geocoder = Geocoder(BesttripApp.instance, Locale.getDefault())
        val addressList = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        var address = ""
        addressList[0].apply {
            when {
                maxAddressLineIndex < 0 -> address = "Cannot found your location!"
                maxAddressLineIndex == 0 -> address = getAddressLine(0)
                maxAddressLineIndex > 1 -> (0..maxAddressLineIndex).forEach {
                    address += getAddressLine(it)
                }
            }
        }
        return MutableLiveData<String>().apply { value = address }
    }

    override fun getNearbyList(): LiveData<String> {
        TODO("get nearby list")
    }

    override fun getLocationList(searchText: String): LiveData<String> {
        TODO("get location by search")
    }
}