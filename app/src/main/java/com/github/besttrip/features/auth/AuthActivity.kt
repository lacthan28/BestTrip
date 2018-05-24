package com.github.besttrip.features.auth

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
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
import org.jetbrains.anko.longToast
import com.github.besttrip.ACTIVITY_FORGOT_REQUEST_CODE
import com.github.besttrip.LoginState
import com.github.besttrip.R
import com.github.besttrip.extensions.*
import com.github.besttrip.features.auth.forgot.ForgotActivity
import com.github.besttrip.features.home.LandingActivity
import com.github.besttrip.repositories.AuthRepositoryImpl


class AuthActivity : DaggerAppCompatActivity() {
    private val authViewModel: AuthViewModel by lazy { providerAuthViewModel() }
    private var email: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isLogin()) {
            startActivityWithAnim<LandingActivity>(true)
        }

        setContentView(R.layout.activity_auth)
        hideSoftKeyboard()

        prepaidLogoAnimations()

        when (savedInstanceState?.get("menuState")) {
            LoginState.Login -> {
                prepaidLoginView()
                email = savedInstanceState.getString("email")
            }
            LoginState.SignUp -> {
                prepaidSignUpView()
                edtUsername?.setText(savedInstanceState.getString("username"))
                email = savedInstanceState.getString("email")
            }
            else -> prepaidLoginView()
        }

        if (email.isNotEmpty()) edtEmail?.setText(email)

        setOnClickListener()

        authViewModel.loginWithEmail.observe(this, Observer {
            if (it == null) return@Observer
            hideLoading()
            startActivityWithAnim<LandingActivity>(true)
        })

        authViewModel.signUpWithEmail.observe(this, Observer {
            if (it == null) return@Observer
            hideLoading()
            prepaidLoginView()
        })

        authViewModel.error.observe(this, Observer {
            if (it == null) return@Observer
            hideLoading()
            it.printStackTrace()
            longToast(it.localizedMessage)
        })
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.apply {
            putSerializable("menuState", if (tvLogin?.isClickable
                            ?: return) LoginState.Login else LoginState.SignUp)
            if (edtUsername.isVisible()) putString("username", edtUsername?.text?.toString() ?: "")
            putString("email", edtEmail?.text?.toString() ?: "")
        }
    }

    override fun onBackPressed() {
        if (isTaskRoot) showExitDialog() else super.onBackPressed()
    }

    private fun setOnClickListener() {
        edtPassword?.afterTextChange {
            if (it.length in 1..6) edtPassword?.error = getString(R.string.password_to_short)
            if (it.isNotEmpty() && (edtUsername?.text ?: "" in it || it in edtUsername?.text ?: "" || it in edtEmail?.text ?: ""))
                edtPassword?.error = getString(R.string.password_as_same_as_username_or_email)
        }

        edtPasswordConfirm?.afterTextChange {
            if (it.isNotEmpty() && it != edtPassword?.text?.toString() ?: "")
                edtPasswordConfirm?.error = getString(R.string.password_not_match)
        }

        tvSignUp?.setOnClickListener {
            prepaidSignUpView()
            clearText(edtUsername)
        }
        tvLogin?.setOnClickListener {
            prepaidLoginView()
        }
        tvSkip?.setOnClickListener {
            startActivityWithAnim<LandingActivity>(true)
        }
        tvForgot?.setOnClickListener {
            startActivityWithAnimForResult<ForgotActivity>(requestCode = ACTIVITY_FORGOT_REQUEST_CODE, isFinish = false)
        }
        btnEmailLogin?.setOnClickListener {
            showLoading()
            email = edtEmail?.text?.toString() ?: ""
            val password = edtPassword?.text?.toString() ?: ""

            when (tvSignUp.isClickable) {
                true -> if (isValidData(email = email, password = password)) {
                    authViewModel.setLogin(email, password)
                }
                false -> {
                    val username = edtUsername?.text?.toString() ?: ""
                    val confirmPassword = edtPasswordConfirm?.text?.toString() ?: ""
                    if (isValidData(username, email, password, confirmPassword)) {
                        authViewModel.setSignUp(username, email, password)
                    }
                }
            }
        }
    }

    private fun isValidData(username: String? = null, email: String, password: String, confirmPassword: String? = null): Boolean {
        // Check username
        if (username.isNotNullAndIsEmpty()) {
            edtUsername?.error = getString(R.string.empty_field, "Username")
            return false
        }

        // Check email
        if (email.isEmpty()) {
            edtEmail?.error = getString(R.string.empty_field, "Email")
            return false
        } else if (email.invalidEmail()) {
            edtEmail?.error = getString(R.string.email_incorrect_format)
            return false
        }

        // Check password
        if (password.isEmpty()) {
            edtPassword?.error = getString(R.string.empty_field, "Password")
            return false
        }

        // Check confirm password
        if (confirmPassword.isNotNullAndIsEmpty()) {
            edtPasswordConfirm?.error = getString(R.string.empty_field, "Confirm password")
            return false
        }

        // All valid
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ACTIVITY_FORGOT_REQUEST_CODE)
            edtEmail?.setText(data?.getStringExtra("email") ?: "")
    }

    private fun prepaidLogoAnimations() {
        clContainer?.post {
            with(ConstraintSet()) {
                clone(clContainer)
                setGuidelinePercent(R.id.glTop, if (isLandscape()) 0.05f else 0.15f)
                TransitionManager.beginDelayedTransition(clContainer, AutoTransition().apply { duration = 1500 })
                applyTo(clContainer)
            }
        }
    }

    private fun prepaidLoginView() {
        clContainer?.post {
            with(ConstraintSet()) {
                clone(clContainer)

                clear(R.id.edtEmail, TOP)
                connect(R.id.edtEmail, TOP, R.id.tvLogin, BOTTOM)
                clear(R.id.btnFbLogin, TOP)
                connect(R.id.btnFbLogin, TOP, R.id.edtPassword, BOTTOM)
                btnFbLogin?.layoutParams = btnFbLogin.layoutParams.apply { setMargin(R.id.btnFbLogin, TOP, dip(16)) }

                groupHide?.referencedIds = intArrayOf(R.id.edtUsername, R.id.line, R.id.line2, R.id.edtPasswordConfirm)
                groupShow?.referencedIds = intArrayOf(R.id.background, R.id.tvLogin, R.id.tvSignUp, R.id.tvForgot,
                        R.id.edtEmail, R.id.line1, R.id.edtPassword, R.id.btnFbLogin, R.id.btnEmailLogin, R.id.tvSkip)

                TransitionManager.beginDelayedTransition(clContainer, AutoTransition().apply { duration = 500 })
                applyTo(clContainer)
            }

            tvSignUp?.enableClick(true)
            tvLogin?.enableClick(false)

            edtEmail?.setBackgroundResource(R.drawable.bg_white_radius_top)
            edtPassword?.setBackgroundResource(R.drawable.bg_white_radius_bottom)

            btnEmailLogin?.text = getString(R.string.login_with_email)

            clearText(edtPassword)
        }
    }

    private fun prepaidSignUpView() {
        clContainer?.post {
            with(ConstraintSet()) {
                clone(clContainer)

                clear(R.id.edtEmail, TOP)
                connect(R.id.edtEmail, TOP, R.id.line, BOTTOM)
                clear(R.id.btnFbLogin, TOP)
                connect(R.id.btnFbLogin, TOP, R.id.edtPasswordConfirm, BOTTOM)
                btnFbLogin?.layoutParams = btnFbLogin?.layoutParams.apply { setMargin(R.id.btnFbLogin, TOP, dip(16)) }

                groupHide?.referencedIds = intArrayOf(R.id.tvForgot)
                groupShow?.referencedIds = intArrayOf(R.id.background, R.id.tvLogin, R.id.tvSignUp,
                        R.id.edtEmail, R.id.line1, R.id.edtPassword, R.id.edtUsername, R.id.line,
                        R.id.line2, R.id.edtPasswordConfirm, R.id.btnFbLogin, R.id.btnEmailLogin, R.id.tvSkip)

                TransitionManager.beginDelayedTransition(clContainer, AutoTransition().apply { duration = 500 })
                applyTo(clContainer)
            }
            tvSignUp?.enableClick(false)
            tvLogin?.enableClick(true)

            edtEmail?.setBackgroundColor(Color.WHITE)
            edtPassword?.setBackgroundColor(Color.WHITE)

            btnEmailLogin?.text = getString(R.string.sign_up_with_email)

            clearText(edtPassword, edtPasswordConfirm)
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