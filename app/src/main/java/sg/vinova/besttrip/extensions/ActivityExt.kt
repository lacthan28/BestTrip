package sg.vinova.besttrip.extensions

import android.app.Activity
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import sg.vinova.besttrip.BesttripApp

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

inline fun <reified T : Activity> Activity.startActivityWithAnimForResult(requestCode: Int, isFinish: Boolean = true) {
    startActivityForResult<T>(requestCode)
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    if (isFinish) finish()
}

inline fun <reified T : Activity> Activity.startActivityWithAnim(isFinish: Boolean = true) {
    startActivity<T>()
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    if (isFinish) finish()
}