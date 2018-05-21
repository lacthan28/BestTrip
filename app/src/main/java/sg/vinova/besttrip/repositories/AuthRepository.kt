package sg.vinova.besttrip.repositories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

interface AuthRepository {
    fun login(user: Pair<String, String>, error: MutableLiveData<Throwable>): LiveData<AuthResult>?
    fun signUp(user: Pair<String, String>, error: MutableLiveData<Throwable>): LiveData<AuthResult>?
    fun forgotPassword(email: String, error: MutableLiveData<Throwable>): LiveData<Void>?
}

class AuthRepositoryImpl : AuthRepository {
    override fun login(user: Pair<String, String>, error: MutableLiveData<Throwable>): LiveData<AuthResult>? {
        var result: LiveData<AuthResult>? = null
        FirebaseAuth.getInstance().signInWithEmailAndPassword(user.first, user.second)
                .addOnSuccessListener { result = MutableLiveData<AuthResult>().apply { value = it } }
                .addOnFailureListener { error.value = it }
        return result
    }

    override fun signUp(user: Pair<String, String>, error: MutableLiveData<Throwable>): LiveData<AuthResult>? {
        var result: LiveData<AuthResult>? = null
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.first, user.second)
                .addOnSuccessListener { result = MutableLiveData<AuthResult>().apply { value = it } }
                .addOnFailureListener { error.value = it }
        return result
    }

    override fun forgotPassword(email: String, error: MutableLiveData<Throwable>): LiveData<Void>? {
        var result: LiveData<Void>? = null
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    result = MutableLiveData<Void>().apply { value = it }
                }
                .addOnFailureListener {
                    error.value = it
                }
        return result
    }
}