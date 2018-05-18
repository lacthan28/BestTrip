package sg.vinova.besttrip

import com.google.firebase.auth.FirebaseAuth
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import sg.vinova.besttrip.dagger.DaggerAppComponent
import sg.vinova.besttrip.extensions.getUser
import sg.vinova.besttrip.models.User
import javax.inject.Inject

class BesttripApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerAppComponent.builder().application(this).build()

    var currentUser: User? = null

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate() {
        super.onCreate()

        instance = this

        if (BuildConfig.DEBUG) {

        }
        currentUser = getUser()
    }

    fun reloadUser() {
        currentUser = getUser()
    }

    companion object {
        lateinit var instance: BesttripApp
            private set
    }
}