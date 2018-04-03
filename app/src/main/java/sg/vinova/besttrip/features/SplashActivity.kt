package sg.vinova.besttrip.features

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.startActivity
import sg.vinova.besttrip.R
import sg.vinova.besttrip.extensions.finishWithLogout
import sg.vinova.besttrip.extensions.isLogin
import sg.vinova.besttrip.features.auth.AuthActivity
import sg.vinova.besttrip.features.home.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (!isLogin()) startActivity<AuthActivity>()
        else {
            //TODO: Login
            startActivity<MainActivity>()
        }
        finishWithLogout()
    }
}
