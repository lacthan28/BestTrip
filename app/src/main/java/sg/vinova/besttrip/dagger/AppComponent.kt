package sg.vinova.besttrip.dagger

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import sg.vinova.besttrip.BesttripApp
import sg.vinova.besttrip.dagger.module.ActivityContributorModule
import sg.vinova.besttrip.dagger.module.AppModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivityContributorModule::class])
interface AppComponent : AndroidInjector<DaggerApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: BesttripApp): Builder

        fun build(): AppComponent
    }
}