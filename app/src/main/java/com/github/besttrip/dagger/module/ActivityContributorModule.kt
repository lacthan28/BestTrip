package com.github.besttrip.dagger.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.github.besttrip.features.auth.AuthActivity
import com.github.besttrip.features.auth.forgot.ForgotActivity
import com.github.besttrip.features.home.LandingActivity

@Module
abstract class ActivityContributorModule {
    @ContributesAndroidInjector
    abstract fun contributeAuthActivity(): AuthActivity

    @ContributesAndroidInjector
    abstract fun contributeForgotActivity(): ForgotActivity

    @ContributesAndroidInjector
    abstract fun contributeLandingActivity(): LandingActivity
}