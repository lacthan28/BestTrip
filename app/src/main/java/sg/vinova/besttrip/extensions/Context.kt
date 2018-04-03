package sg.vinova.besttrip.extensions

import android.content.Context
import android.content.Context.MODE_PRIVATE
import sg.vinova.besttrip.models.User

fun Context.saveUser(user: User) {
    getSharedPreferences("besttrip", MODE_PRIVATE)
            .edit().apply {
                putString("username", user.username)
                putString("email", user.email)
                putString("avatar", user.avatar)
                putString("password", user.password)
            }.apply()

}

fun Context.getUser(): User? {
    var user: User? = null
    getSharedPreferences("besttrip", MODE_PRIVATE).apply {
        val username = getString("username", null)
        val email = getString("email", null)
        val avatar = getString("avatar", null)
        val password = getString("password", null)
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) return null
        user = User(username, email, avatar, password)
    }
    return user
}

fun Context.deleteUser() {
    getSharedPreferences("besttrip", MODE_PRIVATE).edit().clear().apply()
}