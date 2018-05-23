package sg.vinova.besttrip

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import sg.vinova.besttrip.dagger.DaggerAppComponent

class BesttripApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerAppComponent.builder().application(this).build()

    override fun onCreate() {
        super.onCreate()

        instance = this

//        if (BuildConfig.DEBUG) {
//        }
    }

    companion object {
        lateinit var instance: BesttripApp
            private set
    }
}