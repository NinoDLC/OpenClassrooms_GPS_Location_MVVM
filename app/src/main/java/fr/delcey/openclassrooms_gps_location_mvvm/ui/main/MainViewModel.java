package fr.delcey.openclassrooms_gps_location_mvvm.ui.main;

import android.annotation.SuppressLint;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import fr.delcey.openclassrooms_gps_location_mvvm.data.location.LocationRepository;
import fr.delcey.openclassrooms_gps_location_mvvm.data.permission_checker.PermissionChecker;

public class MainViewModel extends ViewModel {

    @NonNull
    private final PermissionChecker permissionChecker;

    @NonNull
    private final LocationRepository locationRepository;

    private final LiveData<String> gpsMessageLiveData;

    public MainViewModel(
        @NonNull PermissionChecker permissionChecker,
        @NonNull LocationRepository locationRepository
    ) {
        this.permissionChecker = permissionChecker;
        this.locationRepository = locationRepository;

        gpsMessageLiveData = Transformations.map(locationRepository.getLocationLiveData(), location -> {
            if (location == null) {
                return "Je suis perdu...";
            } else {
                return "Je suis aux coordonnées (" + location.getLatitude() + "," + location.getLongitude() + ")";
            }
        });
    }

    @SuppressLint("MissingPermission")
    public void refresh() {
        // No GPS permission
        if (!permissionChecker.hasLocationPermission()) {
            locationRepository.stopLocationRequest();
        } else {
            locationRepository.startLocationRequest();
        }
    }

    public LiveData<String> getGpsMessageLiveData() {
        return gpsMessageLiveData;
    }
}