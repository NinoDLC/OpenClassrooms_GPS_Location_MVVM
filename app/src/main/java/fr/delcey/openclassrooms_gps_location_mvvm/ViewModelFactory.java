package fr.delcey.openclassrooms_gps_location_mvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.LocationServices;

import fr.delcey.openclassrooms_gps_location_mvvm.data.location.LocationRepository;
import fr.delcey.openclassrooms_gps_location_mvvm.data.permission_checker.PermissionChecker;
import fr.delcey.openclassrooms_gps_location_mvvm.ui.main.MainViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private volatile static ViewModelFactory sInstance;

    @NonNull
    private final PermissionChecker permissionChecker;

    @NonNull
    private final LocationRepository locationRepository;

    public static ViewModelFactory getInstance() {
        if (sInstance == null) {
            // Double Checked Locking singleton pattern with Volatile works on Android since Honeycomb
            synchronized (ViewModelFactory.class) {
                if (sInstance == null) {
                    Application application = MainApplication.getApplication();

                    sInstance = new ViewModelFactory(
                        new PermissionChecker(
                            application
                        ),
                        new LocationRepository(
                            LocationServices.getFusedLocationProviderClient(
                                application
                            )
                        )
                    );
                }
            }
        }

        return sInstance;
    }

    private ViewModelFactory(
        @NonNull PermissionChecker permissionChecker,
        @NonNull LocationRepository locationRepository
    ) {
        this.permissionChecker = permissionChecker;
        this.locationRepository = locationRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(
                permissionChecker,
                locationRepository
            );
        }
        throw new IllegalArgumentException("Unknown ViewModel class : " + modelClass);
    }
}
