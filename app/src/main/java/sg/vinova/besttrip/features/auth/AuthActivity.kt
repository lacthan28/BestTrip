package sg.vinova.besttrip.features.auth

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.constraint.ConstraintSet.BOTTOM
import android.support.constraint.ConstraintSet.TOP
import android.transition.AutoTransition
import android.transition.TransitionManager
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_auth.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.toast
import sg.vinova.besttrip.ACTIVITY_FORGOT
import sg.vinova.besttrip.LoginState
import sg.vinova.besttrip.R
import sg.vinova.besttrip.extensions.*
import sg.vinova.besttrip.features.auth.forgot.ForgotActivity
import sg.vinova.besttrip.features.home.LandingActivity
import sg.vinova.besttrip.repositories.AuthRepositoryImpl


class AuthActivity : DaggerAppCompatActivity() {
    private val authViewModel: AuthViewModel by lazy { providerAuthViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isLogin()) {
            authViewModel.setUser(getUser())
            authViewModel.result.observe(this, Observer {
                if (it == null) return@Observer
                startActivityWithAnim<LandingActivity>()
            })
        }

        setContentView(R.layout.activity_auth)
        hideSoftKeyboard()

        prepaidLogoAnimations()

        when (savedInstanceState?.get("menuState")) {
            LoginState.Login -> {
                prepaidLoginView()
                edtEmail.setText(savedInstanceState.getString("email"))
            }
            LoginState.SignUp -> {
                prepaidSignUpView()
                edtUsername.setText(savedInstanceState.getString("username"))
                edtEmail.setText(savedInstanceState.getString("email"))
            }
            else -> prepaidLoginView()
        }

        setOnClickListener()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.apply {
            putSerializable("menuState", if (!tvLogin.isClickable) LoginState.Login else LoginState.SignUp)
            if (edtUsername.isVisible()) putString("username", edtUsername.text.toString())
            putString("email", edtEmail.text.toString())
        }
    }

    override fun onBackPressed() {
        if (isTaskRoot) showExitDialog() else super.onBackPressed()
    }

    private fun setOnClickListener() {
        edtPassword.afterTextChange {
            if (it.length in 1..6) edtPassword.error = "Your password should have 6 characters or longer."
            if (it.isNotEmpty() && (it.contains(edtUsername.text)
                            || edtUsername.text.contains(it)
                            || edtEmail.text.contains(it)))
                edtPassword.error = "Your password can not be the same as username or email. Please choose a different password."
        }

        edtPassword2.afterTextChange {
            if (it.isNotEmpty() && it != edtPassword.text.toString())
                edtPassword2.error = "Your passwords don't match. Please retype your password to confirm it."
        }

        tvSignUp.setOnClickListener {
            prepaidSignUpView()
            edtUsername.setText("")
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
                if (isValidData(email = edtEmail.text.toString(), password = edtPassword.text.toString())) {
                    toast("login")
                    //todo(Call api to login)
                }
            } else {
                if (isValidData(username = edtUsername.text.toString(), email = edtEmail.text.toString(),
                                password = edtPassword.text.toString(), confirmPassword = edtPassword2.text.toString())) {
                    toast("sign up")
                    //todo(Call api to sign up)
                }
            }
        }
    }

    private fun isValidData(username: String? = null, email: String, password: String, confirmPassword: String? = null): Boolean {
        if (username.isNotNullAndIsEmpty()) {
            edtUsername.error = "Username is empty"
            return false
        }

        if (email.isEmpty()) {
            edtEmail.error = "Email is empty"
            return false
        } else if (email.invalidEmail()) {
            edtEmail.error = "Your email is not in the correct format"
            return false
        }

        if (password.isEmpty()) {
            edtPassword.error = "Password is empty"
            return false
        }

        if (confirmPassword.isNotNullAndIsEmpty()) {
            edtPassword2.error = "Your confirm password is empty"
            return false
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ACTIVITY_FORGOT)
            edtEmail.setText(data?.getStringExtra("email") ?: "")
    }

    private fun prepaidLogoAnimations() {
        clContainer.post {
            with(ConstraintSet()) {
                clone(clContainer)
                if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
                    setGuidelinePercent(R.id.glTop, 0.05f)
                else setGuidelinePercent(R.id.glTop, 0.15f)
                TransitionManager.beginDelayedTransition(clContainer, AutoTransition().apply {
                    duration = 1500
                })
                applyTo(clContainer)
            }
        }
    }

    private fun prepaidLoginView() {
        clContainer.post {
            with(ConstraintSet()) {
                clone(clContainer)

                clear(R.id.edtEmail, TOP)
                connect(R.id.edtEmail, TOP, R.id.tvLogin, BOTTOM)
                clear(R.id.btnFbLogin, TOP)
                connect(R.id.btnFbLogin, TOP, R.id.edtPassword, BOTTOM)
                btnFbLogin.layoutParams = btnFbLogin.layoutParams.apply { setMargin(R.id.btnFbLogin, TOP, dip(16)) }

                groupHide.referencedIds = intArrayOf(R.id.edtUsername, R.id.line, R.id.line2, R.id.edtPassword2)
                groupShow.referencedIds = intArrayOf(R.id.background, R.id.tvLogin, R.id.tvSignUp, R.id.tvForgot,
                        R.id.edtEmail, R.id.line1, R.id.edtPassword, R.id.btnFbLogin, R.id.btnEmailLogin, R.id.tvSkip)

                TransitionManager.beginDelayedTransition(clContainer, AutoTransition().apply {
                    duration = 500
                })
                applyTo(clContainer)
            }

            tvLogin.isClickable = false
            tvLogin.setTextColor(Color.WHITE)

            tvSignUp.isClickable = true
            tvSignUp.setTextColor(Color.DKGRAY)

            edtEmail.setBackgroundResource(R.drawable.bg_white_radius_top)
            edtPassword.setBackgroundResource(R.drawable.bg_white_radius_bottom)

            btnEmailLogin.text = getString(R.string.login_with_email)

            edtPassword.setText("")
        }
    }

    private fun prepaidSignUpView() {
        clContainer.post {
            with(ConstraintSet()) {
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

                TransitionManager.beginDelayedTransition(clContainer, AutoTransition().apply {
                    duration = 500
                })
                applyTo(clContainer)
            }
            tvSignUp.isClickable = false
            tvSignUp.setTextColor(Color.WHITE)

            tvLogin.isClickable = true
            tvLogin.setTextColor(Color.DKGRAY)

            edtEmail.setBackgroundColor(Color.WHITE)
            edtPassword.setBackgroundColor(Color.WHITE)

            btnEmailLogin.text = getString(R.string.sign_up_with_email)

            edtPassword.setText("")
            edtPassword2.setText("")
        }
    }

    private fun providerAuthViewModel(): AuthViewModel =
            ViewModelProviders.of(this@AuthActivity, object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    val repo = AuthRepositoryImpl()
                    @Suppress("UNCHECKED_CAST")
                    return AuthViewModel(repo) as T
                }
            })[AuthViewModel::class.java]
}