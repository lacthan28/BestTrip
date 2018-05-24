package com.github.besttrip.features.auth

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.google.firebase.auth.AuthResult
import com.github.besttrip.repositories.AuthRepository
import com.github.besttrip.repositories.livedata.AbsentLiveData

class AuthViewModel(repo: AuthRepository) : ViewModel() {
    private val mUserLogin = MutableLiveData<Pair<String, String>?>()
    private val mUserSignUp = MutableLiveData<Triple<String, String, String>?>()
    val loginWithEmail: LiveData<AuthResult>
    val signUpWithEmail: LiveData<AuthResult>
    val logout: LiveData<String>
    val error = MutableLiveData<Throwable>()

    fun setLogin(email: String, password: String) {
        mUserLogin.value = Pair(email, password)
    }

    fun setSignUp(username:String, email: String, password: String) {
        mUserSignUp.value = Triple(username, email, password)
    }

    init {
        loginWithEmail = Transformations.switchMap<Pair<String, String>?, AuthResult>(mUserLogin, {
            if (it == null) {
                AbsentLiveData.create()
            } else {
                repo.login(it, error)
            }
        })

        signUpWithEmail = Transformations.switchMap<Triple<String, String, String>?, AuthResult>(mUserSignUp, {
            if (it == null) {
                AbsentLiveData.create()
            } else {
                repo.signUp(it, error)
            }
        })

        logout = repo.logout()
    }
}