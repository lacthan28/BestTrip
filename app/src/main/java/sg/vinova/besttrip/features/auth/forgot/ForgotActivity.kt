package sg.vinova.besttrip.features.auth.forgot

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import sg.vinova.besttrip.R


class ForgotActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)

        actionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.forgot_password_2)
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
