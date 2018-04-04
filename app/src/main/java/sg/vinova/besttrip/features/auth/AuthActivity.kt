package sg.vinova.besttrip.features.auth

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.constraint.ConstraintSet.BOTTOM
import android.support.constraint.ConstraintSet.TOP
import android.support.v7.app.AppCompatActivity
import android.transition.AutoTransition
import android.transition.TransitionManager
import kotlinx.android.synthetic.main.activity_auth.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.toast
import sg.vinova.besttrip.ACTIVITY_FORGOT
import sg.vinova.besttrip.R
import sg.vinova.besttrip.extensions.*
import sg.vinova.besttrip.features.auth.forgot.ForgotActivity
import sg.vinova.besttrip.features.home.LandingActivity


class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isLogin()) {
            //Todo(Login to account to firebase - logic)
            delay(2000, {
                startActivityWithAnim<LandingActivity>()
            })
        }

        setContentView(R.layout.activity_auth)
    }

    override fun onResume() {
        super.onResume()
        prepaidLogoAnimations()

        prepaidLoginView()

        setOnClickListener()
    }

    private fun setOnClickListener() {
        edtPassword2.afterTextChange {
            if (it.isNotEmpty() && it != edtPassword.text.toString())
                edtPassword2.error = "Your passwords don't match. Please retype your password to confirm it."
        }

        tvSignUp.setOnClickListener {
            prepaidSignUpView()
        }
        tvLogin.setOnClickListener {
            prepaidLoginView()
        }
        tvSkip.setOnClickListener {
            startActivityWithAnim<LandingActivity>()
        }
        tvForgot.setOnClickListener {
            startActivityWithAnimForResult<ForgotActivity>(requestCode = ACTIVITY_FORGOT, isFinish = false)
        }
        btnEmailLogin.setOnClickListener {
            if (btnEmailLogin.text.contains("Login")) {
                if (checkData(email = edtEmail.text.toString(), password = edtPassword.text.toString())) {
                    toast("login")
                    //todo(Call api to login)
                }
            } else {
                if (checkData(username = edtUsername.text.toString(), email = edtEmail.text.toString(),
                                password = edtPassword.text.toString(), confirmPassword = edtPassword2.text.toString())) {
                    toast("sign up")
                    //todo(Call api to sign up)
                }
            }
        }
    }

    private fun checkData(username: String? = null, email: String, password: String, confirmPassword: String? = null): Boolean {
        if (username != null) {
            //todo(check data)
        }
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ACTIVITY_FORGOT)
            edtEmail.setText(data?.getStringExtra("email") ?: "")
    }

    private fun prepaidLogoAnimations() {
        clContainer.post {
            ConstraintSet().apply {
                clone(clContainer)
                setGuidelinePercent(R.id.guideline, 0.15f)
                TransitionManager.beginDelayedTransition(clContainer, AutoTransition().apply {
                    duration = 1000
                })
                applyTo(clContainer)
            }
        }
    }

    private fun prepaidLoginView() {
        clContainer.post {
            ConstraintSet().apply {
                clone(clContainer)

                clear(R.id.edtEmail, TOP)
                connect(R.id.edtEmail, TOP, R.id.tvLogin, BOTTOM)
                clear(R.id.btnFbLogin, TOP)
                connect(R.id.btnFbLogin, TOP, R.id.edtPassword, BOTTOM)
                btnFbLogin.layoutParams = btnFbLogin.layoutParams.apply { setMargin(R.id.btnFbLogin, TOP, dip(16)) }

                groupHide.referencedIds = intArrayOf(R.id.edtUsername, R.id.line, R.id.line2, R.id.edtPassword2)
                groupShow.referencedIds = intArrayOf(R.id.background, R.id.tvLogin, R.id.tvSignUp, R.id.tvForgot,
                        R.id.edtEmail, R.id.line1, R.id.edtPassword, R.id.btnFbLogin, R.id.btnEmailLogin, R.id.tvSkip)

                tvLogin.isClickable = false
                tvLogin.setTextColor(Color.WHITE)
                tvLogin.alpha = 1f

                tvSignUp.isClickable = true
                tvSignUp.setTextColor(Color.DKGRAY)
                tvSignUp.alpha = 0.7f

                edtEmail.setBackgroundResource(R.drawable.bg_white_radius_top)
                edtPassword.setBackgroundResource(R.drawable.bg_white_radius_bottom)

                btnEmailLogin.text = getString(R.string.login_with_email)

                edtPassword.setText("")

                TransitionManager.beginDelayedTransition(clContainer, AutoTransition().apply {
                    duration = 500
                })
                applyTo(clContainer)
            }
        }
    }

    private fun prepaidSignUpView() {
        clContainer.post {
            ConstraintSet().apply {
                clone(clContainer)

                clear(R.id.edtEmail, TOP)
                connect(R.id.edtEmail, TOP, R.id.line, BOTTOM)
                clear(R.id.btnFbLogin, TOP)
                connect(R.id.btnFbLogin, TOP, R.id.edtPassword2, BOTTOM)
                btnFbLogin.layoutParams = btnFbLogin.layoutParams.apply { setMargin(R.id.btnFbLogin, TOP, dip(16)) }

                groupHide.referencedIds = intArrayOf(R.id.tvForgot)
                groupShow.referencedIds = intArrayOf(R.id.background, R.id.tvLogin, R.id.tvSignUp,
                        R.id.edtEmail, R.id.line1, R.id.edtPassword, R.id.edtUsername, R.id.line,
                        R.id.line2, R.id.edtPassword2, R.id.btnFbLogin, R.id.btnEmailLogin, R.id.tvSkip)

                tvSignUp.isClickable = false
                tvSignUp.setTextColor(Color.WHITE)
                tvSignUp.alpha = 1f

                tvLogin.isClickable = true
                tvLogin.setTextColor(Color.DKGRAY)
                tvLogin.alpha = 0.7f

                edtEmail.setBackgroundColor(Color.WHITE)
                edtPassword.setBackgroundColor(Color.WHITE)

                btnEmailLogin.text = getString(R.string.sign_up_with_email)

                edtUsername.setText("")
                edtPassword.setText("")
                edtPassword2.setText("")

                TransitionManager.beginDelayedTransition(clContainer, AutoTransition().apply {
                    duration = 500
                })
                applyTo(clContainer)
            }
        }
    }
}