package com.github.besttrip.dagger

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import com.github.besttrip.BesttripApp
import com.github.besttrip.dagger.module.ActivityContributorModule
import com.github.besttrip.dagger.module.AppModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivityContributorModule::class])
interface AppComponent : AndroidInjector<BesttripApp> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: BesttripApp): Builder

        fun build(): AppComponent
    }
}