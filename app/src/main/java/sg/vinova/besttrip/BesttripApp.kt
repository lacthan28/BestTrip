package sg.vinova.besttrip

import com.facebook.stetho.Stetho
import com.google.firebase.auth.FirebaseAuth
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import sg.vinova.besttrip.dagger.DaggerAppComponent
import javax.inject.Inject

class BesttripApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerAppComponent.builder().application(this).build()

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate() {
        super.onCreate()

        instance = this

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

    companion object {
        lateinit var instance: BesttripApp
            private set
    }
}