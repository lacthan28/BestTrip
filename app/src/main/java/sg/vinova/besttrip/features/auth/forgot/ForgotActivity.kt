package sg.vinova.besttrip.features.auth.forgot

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_forgot.*
import org.jetbrains.anko.toast
import sg.vinova.besttrip.R
import sg.vinova.besttrip.extensions.invalidEmail
import sg.vinova.besttrip.repositories.AuthRepositoryImpl


class ForgotActivity : DaggerAppCompatActivity() {
    private val forgotViewModel: ForgotViewModel by lazy { createForgotViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)

        actionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.forgot_password_2) ?: ""

        setOnClickListener()
    }

    private fun setOnClickListener() {
        tvSendForgot?.setOnClickListener {
            val email = edtForgotEmail?.text?.toString()?:""
            when {
                email.isEmpty() -> edtForgotEmail?.error = "Please input your email!"
                email.invalidEmail() -> edtForgotEmail?.error = "Invalid email!"
                else -> {
                    forgotViewModel.setEmail(email).forgotPassword.observe(this, Observer {
                        if (it == null) return@Observer
                        setResult(Activity.RESULT_OK, Intent().apply { putExtra("email", email) })
                        finish()
                    })
                    forgotViewModel.error.observe(this, Observer {
                        toast(it?.localizedMessage ?: "UNKNOWN ERROR")
                    })
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createForgotViewModel(): ForgotViewModel = ViewModelProviders.of(this, object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ForgotViewModel(AuthRepositoryImpl()) as T
        }
    })[ForgotViewModel::class.java]
}
