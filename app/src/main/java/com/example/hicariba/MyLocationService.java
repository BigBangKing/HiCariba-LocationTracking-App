package com.example.hicariba;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

//TODO: Integrate with server and collect and send locations periodically.

public class MyLocationService extends Service {
    public MyLocationService() {
    }

    Handler handler;
    Runnable r;
    static int i;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler = new Handler();
        onTaskRemoved(intent);
        stopSelf();
        Toast.makeText(getApplicationContext(), "This is a Service running in Background",
                Toast.LENGTH_SHORT).show();
        startLogging();
        return START_NOT_STICKY;

    }
    private void startLogging() {

//        final LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

        r = new Runnable() {
            public void run() {

                //we don't need to check permission again since it is started after this permission was granted. So ignore error below.
                /*
                if (locationManager == null) {
                    return;
                }

                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                */
                // Toast.makeText(getApplicationContext(),"Got Location in Background: lat"+ location.getLatitude()+ " long: "+location.getLatitude(), Toast.LENGTH_SHORT).show();

                i++;

                Toast.makeText(getApplicationContext(), "running in Background for  "+(i*5),
                        Toast.LENGTH_SHORT).show();

                handler.postDelayed(this, 5000);
            }
        };

        handler.postDelayed(r, 5000);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        /*Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
        restartServiceIntent.setPackage(getPackageName());
        startService(restartServiceIntent);*/
        Toast.makeText(getApplicationContext(), "Service is removed since it is not required.", Toast.LENGTH_SHORT).show();

        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(), "It is destroyed since it is not required.", Toast.LENGTH_SHORT).show();
        handler.removeCallbacks(r);

        super.onDestroy();
    }

}
