package sg.vinova.besttrip.features.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import sg.vinova.besttrip.R
import sg.vinova.besttrip.extensions.showExitDialog

class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
    }

    override fun onBackPressed() {
        if (isTaskRoot) showExitDialog()
        else super.onBackPressed()
    }
}