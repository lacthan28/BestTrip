package sg.vinova.besttrip.dagger.module

import android.app.Application
import android.os.Handler
import android.os.Looper
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import dagger.Binds
import dagger.Module
import dagger.Provides
import sg.vinova.besttrip.BesttripApp


@Module
class AppModule {
    @Module
    interface ApplicationDeclarations {
        @Binds
        fun provideApplication(app: BesttripApp): Application
    }

    @Provides
    fun provideMainHandler(): Handler = Handler(Looper.getMainLooper())

    @Provides
    fun provideGoogleApiClient(context: BesttripApp): GoogleApiClient = GoogleApiClient.Builder(context)
            .addApi(LocationServices.API)
            .build()

    @Provides
    fun provideLocationRequest(): LocationRequest = LocationRequest.create().apply {
        interval = 10000
        fastestInterval = 5000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }
}