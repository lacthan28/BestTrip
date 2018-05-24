package com.github.besttrip.extensions

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.res.Configuration
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import com.github.besttrip.R

const val LOADING_TAG = "ProgressDialogFragment"


inline fun <reified T : Activity> T.isLogin() = FirebaseAuth.getInstance().currentUser != null

inline fun <reified T : Activity> Activity.startActivityWithAnimForResult(requestCode: Int, isFinish: Boolean = true) {
    startActivityForResult<T>(requestCode)
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    if (isFinish) finish()
}

inline fun <reified T : Activity> Activity.startActivityWithAnim(isFinish: Boolean = false) {
    startActivity<T>()
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    if (isFinish) finish()
}

inline fun <reified T : Activity> T.hideSoftKeyboard() {
    val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}

fun Activity.showExitDialog() {
    AlertDialog.Builder(this, R.style.ExitDialog)
            .setTitle("Exit ${getString(R.string.app_name)}")
            .setMessage("Are you want to exit the application?")
            .setCancelable(false)
            .setNegativeButton("Yes", { _, _ -> finish() })
            .setPositiveButton("No", { dialog, _ -> dialog.dismiss() })
            .create()
            .show()
}

inline fun <reified T : Activity> T.getColorCompat(idRes: Int) = ContextCompat.getColor(this, idRes)

fun Activity.showLoading() {
    findViewById<ProgressBar>(R.id.progressBar)?.visibility = View.VISIBLE
}

fun Activity.hideLoading() {
    findViewById<ProgressBar>(R.id.progressBar)?.visibility = View.GONE
}

fun Context.isLandscape(): Boolean = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE