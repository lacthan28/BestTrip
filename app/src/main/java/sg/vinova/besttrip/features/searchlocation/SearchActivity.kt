package sg.vinova.besttrip.features.searchlocation

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import sg.vinova.besttrip.R

class SearchActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }
}
