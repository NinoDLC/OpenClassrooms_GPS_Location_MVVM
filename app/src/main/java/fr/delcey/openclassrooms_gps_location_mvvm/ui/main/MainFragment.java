package fr.delcey.openclassrooms_gps_location_mvvm.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import fr.delcey.openclassrooms_gps_location_mvvm.R;
import fr.delcey.openclassrooms_gps_location_mvvm.ViewModelFactory;

public class MainFragment extends Fragment {

    private MainViewModel viewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MainViewModel.class);

        TextView messageTextView = view.findViewById(R.id.main_textview_message);
        Button gpsPermissionButton = view.findViewById(R.id.main_button_request_permissions);

        gpsPermissionButton.setOnClickListener(v ->
            ActivityCompat.requestPermissions(
                requireActivity(),
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0
            )
        );

        viewModel.getGpsMessageLiveData().observe(getViewLifecycleOwner(), message -> messageTextView.setText(message));
    }

    @Override
    public void onResume() {
        super.onResume();

        viewModel.refresh();
    }
}