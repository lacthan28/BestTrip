package sg.vinova.besttrip.features.auth.forgot

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_forgot.*
import sg.vinova.besttrip.R


class ForgotActivity : AppCompatActivity() {

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
            if (email.isNotEmpty()) {
                //todo(If call api to send email reset pass success)
                setResult(Activity.RESULT_OK, Intent().apply { putExtra("email", email) })
                finish()
            } else edtForgotEmail.error = "Please input your email!"
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
