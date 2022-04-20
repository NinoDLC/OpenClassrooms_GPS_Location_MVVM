package fr.delcey.openclassrooms_gps_location_mvvm.ui.main;

import android.annotation.SuppressLint;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import fr.delcey.openclassrooms_gps_location_mvvm.data.location.LocationRepository;
import fr.delcey.openclassrooms_gps_location_mvvm.data.permission_checker.PermissionChecker;

public class MainViewModel extends ViewModel {

    @NonNull
    private final PermissionChecker permissionChecker;

    @NonNull
    private final LocationRepository locationRepository;

    private final MediatorLiveData<String> gpsMessageLiveData = new MediatorLiveData<>();

    private final MutableLiveData<Boolean> hasGpsPermissionLiveData = new MutableLiveData<>();

    public MainViewModel(
        @NonNull PermissionChecker permissionChecker,
        @NonNull LocationRepository locationRepository
    ) {
        this.permissionChecker = permissionChecker;
        this.locationRepository = locationRepository;

        LiveData<Location> locationLiveData = locationRepository.getLocationLiveData();

        gpsMessageLiveData.addSource(locationLiveData, location ->
            combine(location, hasGpsPermissionLiveData.getValue())
        );

        gpsMessageLiveData.addSource(hasGpsPermissionLiveData, hasGpsPermission ->
            combine(locationLiveData.getValue(), hasGpsPermission)
        );
    }

    private void combine(@Nullable Location location, @Nullable Boolean hasGpsPermission) {
        if (location == null) {
            if (hasGpsPermission == null || !hasGpsPermission) {
                // Never hardcode translatable Strings, always use Context.getString(R.string.my_string) instead !
                gpsMessageLiveData.setValue("I am lost... Should I click the permission button ?!");
            } else {
                gpsMessageLiveData.setValue("Querying location, please wait for a few seconds...");
            }
        } else {
            gpsMessageLiveData.setValue("I am at coordinates (lat:" + location.getLatitude() + ", long:" + location.getLongitude() + ")");
        }
    }

    @SuppressLint("MissingPermission")
    public void refresh() {
        boolean hasGpsPermission = permissionChecker.hasLocationPermission();
        hasGpsPermissionLiveData.setValue(hasGpsPermission);

        if (hasGpsPermission) {
            locationRepository.startLocationRequest();
        } else {
            locationRepository.stopLocationRequest();
        }
    }

    public LiveData<String> getGpsMessageLiveData() {
        return gpsMessageLiveData;
    }
}