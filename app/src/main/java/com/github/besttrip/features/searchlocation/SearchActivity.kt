package com.github.besttrip.features.searchlocation

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_search.*
import com.github.besttrip.R
import com.github.besttrip.extensions.afterTextChange
import com.github.besttrip.extensions.hideLoading
import com.github.besttrip.repositories.LocationRepositoryImpl

class SearchActivity : DaggerAppCompatActivity() {
    private val searchViewModel: SearchViewModel by lazy { createSearchViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        rvLocation?.layoutManager = LinearLayoutManager(this).apply { orientation = LinearLayoutManager.VERTICAL }
        searchViewModel.locationList.observe(this, Observer {

        })

        edtSearch?.afterTextChange {
            hideLoading()
            searchViewModel.setSearchText(it)
        }
    }

    private fun createSearchViewModel(): SearchViewModel = ViewModelProviders.of(this, object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(LocationRepositoryImpl()) as T
        }
    })[SearchViewModel::class.java]
}
