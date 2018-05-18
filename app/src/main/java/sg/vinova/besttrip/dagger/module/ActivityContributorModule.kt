package sg.vinova.besttrip.dagger.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import sg.vinova.besttrip.features.auth.AuthActivity
import sg.vinova.besttrip.features.auth.forgot.ForgotActivity
import sg.vinova.besttrip.features.home.LandingActivity

@Module
abstract class ActivityContributorModule {
    @ContributesAndroidInjector
    abstract fun contributeAuthActivity(): AuthActivity

    @ContributesAndroidInjector
    abstract fun contributeForgotActivity(): ForgotActivity

    @ContributesAndroidInjector
    abstract fun contributeLandingActivity(): LandingActivity
}