package sg.vinova.besttrip.repositories.livedatas

import android.arch.lifecycle.LiveData
import com.google.firebase.auth.AuthResult

class AuthResultLiveData(private val authResult: AuthResult) : LiveData<AuthResult>() {
    override fun onActive() {
        super.onActive()
        value = authResult
    }
}