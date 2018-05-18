package sg.vinova.besttrip.dagger.module

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.Provides
import sg.vinova.besttrip.BesttripApp
import javax.inject.Singleton

@Module
abstract class AppModule {
    @Binds
    abstract fun bindContext(app: BesttripApp): Context

    @Module
    companion object {
        @Provides
        fun provideMainHandler() = Handler(Looper.getMainLooper())

        @Provides
        @Singleton
        fun provideFirebaseAuthInstance() = FirebaseAuth.getInstance()

        @Provides
        @Singleton
        fun provideGGClient(app: BesttripApp) = GoogleApiClient.Builder(app)
                .addApi(LocationServices.API)
                .build()
    }
}