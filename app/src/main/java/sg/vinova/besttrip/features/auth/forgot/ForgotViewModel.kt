package sg.vinova.besttrip.features.auth.forgot

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import sg.vinova.besttrip.repositories.AuthRepository
import sg.vinova.besttrip.repositories.livedatas.AbsentLiveData

class ForgotViewModel(repo: AuthRepository) : ViewModel() {
    private val emailLiveData = MutableLiveData<String?>()
    val error = MutableLiveData<Throwable>()
    val forgotPassword: LiveData<Void>

    fun setEmail(email: String): ForgotViewModel {
        emailLiveData.value = email
        return this
    }

    init {
        forgotPassword = Transformations.switchMap<String, Void>(emailLiveData, {
            if (it.isNullOrEmpty()) {
                AbsentLiveData.create()
            } else {
                repo.forgotPassword(it, error)
            }
        })
    }
}