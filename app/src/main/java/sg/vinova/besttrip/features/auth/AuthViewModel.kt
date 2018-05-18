package sg.vinova.besttrip.features.auth

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.google.firebase.auth.AuthResult
import sg.vinova.besttrip.models.User
import sg.vinova.besttrip.repositories.AuthRepository
import sg.vinova.besttrip.repositories.livedatas.AbsentLiveData

class AuthViewModel(repo: AuthRepository) : ViewModel() {
    val result: LiveData<AuthResult>
    val error = MutableLiveData<Throwable>()
    private val user: MutableLiveData<User?>()
    fun setUser(user: User?) {
        this.user.value = user
    }

    init {
        result = Transformations.switchMap<User, AuthResult>(user, {
            if (it == null) {
                AbsentLiveData.create()
            } else {
                repo.login(it, error)
            }
        })
    }
}