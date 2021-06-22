package fr.delcey.openclassrooms_gps_location_mvvm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import fr.delcey.openclassrooms_gps_location_mvvm.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow();
        }
    }
}