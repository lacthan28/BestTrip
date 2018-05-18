package sg.vinova.besttrip.repositories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import sg.vinova.besttrip.models.User
import sg.vinova.besttrip.repositories.livedatas.AuthResultLiveData

interface AuthRepository {
    fun login(user: User, error: MutableLiveData<Throwable>): LiveData<AuthResult>?
}

class AuthRepositoryImpl : AuthRepository {
    override fun login(user: User, error: MutableLiveData<Throwable>): LiveData<AuthResult>? {
        var result: LiveData<AuthResult>? = null
        FirebaseAuth.getInstance().signInWithEmailAndPassword(user.email ?: "", user.password ?: "")
                .addOnSuccessListener { result = AuthResultLiveData(it) }
                .addOnFailureListener { error.value = it }
        return result
    }
}