package sg.vinova.besttrip.features.auth

import android.animation.TimeInterpolator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import android.transition.AutoTransition
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.OvershootInterpolator
import kotlinx.android.synthetic.main.activity_auth.*
import org.jetbrains.anko.startActivity
import sg.vinova.besttrip.R
import sg.vinova.besttrip.extensions.finishWithLogout
import sg.vinova.besttrip.extensions.isLogin
import sg.vinova.besttrip.features.home.MainActivity

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_auth)

        if (isLogin()) {
            //Todo(Login to account)
            startActivity<MainActivity>()
            finishWithLogout()
        }
    }

    override fun onResume() {
        super.onResume()
        setupAnimations()
    }

    private fun setupAnimations() {
        clContainer.post {
            ConstraintSet().apply {
                clone(clContainer)
                setGuidelinePercent(R.id.guideline, 0.15f)
                TransitionManager.beginDelayedTransition(clContainer, AutoTransition().apply {
                    duration = 1000
                })
                applyTo(clContainer)
            }
        }
    }
}