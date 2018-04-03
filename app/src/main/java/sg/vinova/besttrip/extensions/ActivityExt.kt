package sg.vinova.besttrip.extensions

import android.app.Activity
import sg.vinova.besttrip.BesttripApp
import java.util.logging.Handler

inline fun <reified T : Activity> T.isLogin() = BesttripApp.instance.currentUser != null

inline fun <reified T : Activity> T.finishWithLogout() {
    BesttripApp.instance.firebaseAuth?.signOut()
    finish()
}

fun Activity.delay(duration: Long, init: () -> Unit) {
    runOnUiThread {
        Thread.sleep(duration)
        init()
    }
}