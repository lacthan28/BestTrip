package sg.vinova.besttrip.features.auth.forgot

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_forgot.*
import sg.vinova.besttrip.R
import sg.vinova.besttrip.extensions.invalidEmail


class ForgotActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)

        actionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.forgot_password_2)

        setOnClickListener()
    }

    private fun setOnClickListener() {
        tvSendForgot.setOnClickListener {
            val email = edtForgotEmail.text.toString()
            when {
                email.isEmpty() -> edtForgotEmail.error = "Please input your email!"
                email.invalidEmail() -> edtForgotEmail.error = "Invalid email!"
                else -> {
                    //todo(If call api to send email reset pass success)
                    setResult(Activity.RESULT_OK, Intent().apply { putExtra("email", email) })
                    finish()
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
}
