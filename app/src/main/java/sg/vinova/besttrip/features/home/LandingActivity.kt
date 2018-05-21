package sg.vinova.besttrip.features.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.os.Looper
import android.support.constraint.ConstraintSet
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_landing.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import sg.vinova.besttrip.R
import sg.vinova.besttrip.extensions.*
import javax.inject.Inject

const val LOCATION_PERMISSION_REQUEST_CODE = 240

class LandingActivity : DaggerAppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @Inject
    lateinit var googleApiClient: GoogleApiClient

    @Inject
    lateinit var locationRequest: LocationRequest

    private val locationCallback: LocationCallback by lazy {
        object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult?) {
                p0?.locations?.filterNotNull()?.forEach {
                    hideLoading()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        clContainer?.post {
            ConstraintSet().apply {
                clone(clContainer)
                setGuidelinePercent(R.id.glTop, if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 0.2f else 0.3f)
                applyTo(clContainer)
            }
        }

        requestLocationPermissions()

        showLoading()
    }


    @AfterPermissionGranted(LOCATION_PERMISSION_REQUEST_CODE)
    private fun requestLocationPermissions() {
        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

        if (EasyPermissions.hasPermissions(this, *permissions)) {
            if (isPlayServicesAvailable()) {
                googleApiClient.connect()
                googleApiClient.registerConnectionCallbacks(this)
                googleApiClient.registerConnectionFailedListener(this)
            }
        } else {
            // if permissions are not currently granted, request permissions
            EasyPermissions.requestPermissions(this,
                    getString(R.string.permission_rationale_location),
                    LOCATION_PERMISSION_REQUEST_CODE, *permissions)
        }
    }

    override fun onBackPressed() {
        if (isTaskRoot) showExitDialog()
        else super.onBackPressed()
    }

    @SuppressLint("MissingPermission")
    override fun onConnected(p0: Bundle?) {
        LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    override fun onConnectionSuspended(p0: Int) {
        googleApiClient.connect()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        error("${p0.errorCode} - ${p0.errorMessage ?: ""}")
    }
}