package fr.delcey.openclassrooms_gps_location_mvvm.data.permission_checker;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * This class exists only for easier unit testing the MainViewModel. Ask your mentor if you don't get why ! Hint : dependency injection
 */
public class PermissionChecker {

    @NonNull
    private final Application application;

    public PermissionChecker(@NonNull Application application) {
        this.application = application;
    }

    public boolean hasLocationPermission() {
        return ContextCompat.checkSelfPermission(application, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED;
    }
}