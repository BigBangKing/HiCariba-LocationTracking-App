package com.example.hicariba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class StatusActivity extends AppCompatActivity {
    public static final int REQUEST_LOCATION_PERMISSION = 799;

    Button button;
    AppCompatImageView imageView;
    TextView textView;
    static boolean isTrackerOn;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        isTrackerOn = true;

        imageView = findViewById(R.id.appCompatImageView);
        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggle);
        textView = findViewById(R.id.textView);

        intent = new Intent(getApplicationContext(), MyLocationService.class);

        //request location permission
        requestLocationPermission();

        // Set a checked change listener for toggle button
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    animateImageView();
                    imageView.setImageResource(R.drawable.ic_baseline_check_circle_outline_24);
                    textView.setText("");
                    isTrackerOn = true;

                    //request
                    requestLocationPermission();

                } else {
                    animateImageView();

                    imageView.setImageResource(R.drawable.ic_baseline_close_24);
                    textView.setText("Tracker Off.");
                    isTrackerOn = false;
                    stopLogging();
                }
            }
        });


    }

    private void animateImageView() {
        imageView.animate().scaleX(0.6f).scaleY(0.6f).setDuration(50) // all take 1 seconds
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        //animation ended
                        imageView.animate().scaleX(1f).scaleY(1f).setDuration(100);
                    }
                });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
    public void requestLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
//            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
            if (isTrackerOn)
                StartLoggingLocation();
        } else {
            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, perms);
        }
    }

    private void StartLoggingLocation() {
        startService(intent);
    }

    void stopLogging() {
        stopService(intent);
    }
}