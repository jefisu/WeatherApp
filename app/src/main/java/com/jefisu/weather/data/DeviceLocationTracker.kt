package com.jefisu.weather.data

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.jefisu.weather.R
import com.jefisu.weather.core.util.LocationResult
import com.jefisu.weather.core.util.UiText
import com.jefisu.weather.domain.LocationTracker
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine

class DeviceLocationTracker(
    private val locationClient: FusedLocationProviderClient,
    private val app: Application
) : LocationTracker {

    override suspend fun getCurrentLocation(): LocationResult {
        val hasAccessFineLocationPermission = app.checkSelfPermission(
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasAccessCoarseLocationPermission = app.checkSelfPermission(
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        if (!hasAccessCoarseLocationPermission || !hasAccessFineLocationPermission) {
            return LocationResult(
                data = null,
                isGranted = false,
                error = UiText.StringResource(R.string.declined_permanently_permission)
            )
        }

        val locationManager = app.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!isGpsEnabled) {
            return LocationResult(
                data = null,
                isGranted = true,
                error = UiText.StringResource(R.string.enable_gps)
            )
        }

        return suspendCancellableCoroutine { cont ->
            locationClient.lastLocation.apply {
                addOnSuccessListener {
                    cont.resume(
                        LocationResult(
                            data = it,
                            isGranted = true
                        )
                    )
                }
                addOnFailureListener {
                    cont.resume(
                        LocationResult(
                            data = null,
                            isGranted = true,
                            error = UiText.unknownError()
                        )
                    )
                }
                addOnCanceledListener {
                    cont.cancel()
                }
            }
        }
    }
}