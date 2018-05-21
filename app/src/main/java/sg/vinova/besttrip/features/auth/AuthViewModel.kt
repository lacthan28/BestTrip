package sg.vinova.besttrip.features.auth

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.google.firebase.auth.AuthResult
import sg.vinova.besttrip.repositories.AuthRepository
import sg.vinova.besttrip.repositories.livedatas.AbsentLiveData

class AuthViewModel(repo: AuthRepository) : ViewModel() {
    private val mUser = MutableLiveData<Pair<String, String>?>()
    val loginWithEmail: LiveData<AuthResult>
    val signUpWithEmail: LiveData<AuthResult>
    val error = MutableLiveData<Throwable>()

    fun setEmailPassword(email: String, password: String) {
        mUser.value = Pair(email, password)
    }

    init {
        loginWithEmail = Transformations.switchMap<Pair<String, String>, AuthResult>(mUser, {
            if (it == null) {
                AbsentLiveData.create()
            } else {
                repo.login(it, error)
            }
        })

        signUpWithEmail = Transformations.switchMap<Pair<String, String>, AuthResult>(mUser, {
            if (it == null){
                AbsentLiveData.create()
            } else{
                repo.signUp(it, error)
            }
        })
    }
}