package sample.locationtest;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
//import android.widget.Toast;

public class LocationTest extends Activity implements LocationListener {
    private LocationManager lm;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    @Override
    public void onResume() {
        super.onResume();
        try {
            this.lm = (LocationManager)this.getSystemService(Activity.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            String provider = this.lm.getBestProvider(criteria, true);
            Log.d("LocationTest", "provider:" + provider);
            this.setMessage(provider);
            if (provider != null) {
                Log.d("LocationTest", "enabled:" + this.lm.isProviderEnabled(provider));
                this.setMessage("enabled:" + this.lm.isProviderEnabled(provider));
            }
            if (provider == null || !this.lm.isProviderEnabled(provider)) {
                Log.e("LocationTest", "location service is unabailable. ");
                return;
            }
            this.lm.requestLocationUpdates(provider, 0, 0, this);
//          Location location = this.lm.getLastKnownLocation(provider);
//          Toast.makeText(this, "LastKnownLocation=" + location, Toast.LENGTH_LONG).show();
//          if (location != null) {
//              Toast.makeText(this, "use LastKnownLocation", Toast.LENGTH_LONG).show();
//              this.onLocationChanged(location);
//          }
        } catch (SecurityException e) {
            Log.e("LocationTest", "permission 'android.permission.ACCESS_FINE_LOCATION' not set. check your AndroidManifest.xml");
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        String msg = null;
        msg = "onLocationChanged";
//        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        this.setMessage(msg);
        msg = "lat=" + location.getLatitude() + "&lon=" + location.getLongitude();
//        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        this.setMessage(msg);
    }

    @Override
    public void onProviderDisabled(String provider) {
        String msg = null;
        msg = "onProviderDisabled";
//        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        this.setMessage(msg);
    }

    @Override
    public void onProviderEnabled(String provider) {
        String msg = null;
        msg = "onProviderEnabled";
//        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        this.setMessage(msg);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        String msg = null;
        msg = "onStatusChanged";
//        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        this.setMessage(msg);
        msg = provider + " ";
        switch (status) {
        case LocationProvider.AVAILABLE:
            msg = "AVAILABLE";
            break;
        case LocationProvider.OUT_OF_SERVICE:
            msg = "OUT OF SERVICE";
            break;
        case LocationProvider.TEMPORARILY_UNAVAILABLE:
            msg = "TEMPORARILY UNAVAILABLE";
            break;
        }
//        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        this.setMessage(msg);
    }

    @Override
    public void onPause() {
        super.onPause();
        this.lm.removeUpdates(this);
        this.lm = null;
        Log.d("LocationTest", "removeUpdates");
    }

    private void setMessage(String msg) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String now = formatter.format(new Date());
        TextView view = (TextView)this.findViewById(R.id.TextView01);
        view.setText(view.getText() + System.getProperty("line.separator") + now + ":" + msg);
    }
}