package sg.vinova.besttrip

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import sg.vinova.besttrip.extensions.getUser
import sg.vinova.besttrip.models.User

class BesttripApp : Application() {
    var currentUser: User? = null
    var firebaseAuth: FirebaseAuth? = null

    override fun onCreate() {
        super.onCreate()

        instance = this
        firebaseAuth = FirebaseAuth.getInstance()

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