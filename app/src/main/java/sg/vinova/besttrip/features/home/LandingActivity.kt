package sg.vinova.besttrip.features.home

import android.content.res.Configuration
import android.os.Bundle
import android.support.constraint.ConstraintSet
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_landing.*
import sg.vinova.besttrip.R
import sg.vinova.besttrip.extensions.delay
import sg.vinova.besttrip.extensions.hideLoading
import sg.vinova.besttrip.extensions.showExitDialog
import sg.vinova.besttrip.extensions.showLoading

class LandingActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        clContainer?.post {
            ConstraintSet().apply {
                clone(clContainer)
                setGuidelinePercent(R.id.glTop, if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 0.2f else 0.3f)
                applyTo(clContainer)
            }
        }
    }

    override fun onBackPressed() {
        if (isTaskRoot) showExitDialog()
        else super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()

        showLoading()
        delay(2000, { hideLoading() })
    }
}