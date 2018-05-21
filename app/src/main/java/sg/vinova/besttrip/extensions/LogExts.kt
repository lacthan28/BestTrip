package sg.vinova.besttrip.extensions

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import sg.vinova.besttrip.R

inline fun <reified T : Context> T.warning(message: String) {
    val i = "|   ${T::class.java.simpleName} - $message   |"
    Log.w(getString(R.string.app_name), "-".repeat(i.length))
    Log.w(getString(R.string.app_name), i)
    Log.w(getString(R.string.app_name), "-".repeat(i.length))
}

inline fun <reified T : Context> T.error(message: String) {
    val i = "|   ${T::class.java.simpleName} - $message   |"
    Log.e(getString(R.string.app_name), "-".repeat(i.length))
    Log.e(getString(R.string.app_name), i)
    Log.e(getString(R.string.app_name), "-".repeat(i.length))
}

inline fun <reified T : Context> T.errorWithDialog(message: String) {
    val i = "|   ${T::class.java.simpleName} - $message   |"
    Log.e(getString(R.string.app_name), "-".repeat(i.length))
    Log.e(getString(R.string.app_name), i)
    Log.e(getString(R.string.app_name), "-".repeat(i.length))

    with(AlertDialog.Builder(this)) {
        setTitle("Error!!")
        setMessage(message)
        setNeutralButton("OK", { dialog, _ -> dialog.dismiss() })
    }
}

inline fun <reified T : Context> T.info(message: String) {
    val i = "|   ${T::class.java.simpleName} - $message   |"
    Log.i(getString(R.string.app_name), "-".repeat(i.length))
    Log.i(getString(R.string.app_name), i)
    Log.i(getString(R.string.app_name), "-".repeat(i.length))
}

inline fun <reified T : Context> T.debug(message: String) {
    val i = "|   ${T::class.java.simpleName} - $message   |"
    Log.d(getString(R.string.app_name), "-".repeat(i.length))
    Log.d(getString(R.string.app_name), i)
    Log.d(getString(R.string.app_name), "-".repeat(i.length))
}

inline fun <reified T : Context> T.verbose(message: String) {
    val i = "|   ${T::class.java.simpleName} - $message   |"
    Log.v(getString(R.string.app_name), "-".repeat(i.length))
    Log.v(getString(R.string.app_name), i)
    Log.v(getString(R.string.app_name), "-".repeat(i.length))
}