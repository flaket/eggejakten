package io.flakstad.eggejakten;

import io.flakstad.eggejakten.ColorView.ColorThread;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class FinalActivity extends Activity {

	static final String TAG = "FinalActivity";

	private ColorView mColorView;
	private ColorThread mColorThread;
	LocationManager locationManager;
	String locationProvider;
	Location lastKnownLocation, targetLocation;
	LocationListener locationListener;

	double meter = 1.0;
	int alpha = 255;
	int red = 255;
	int green = 255;
	int blue = 204;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_final);
		mColorView = (ColorView) findViewById(R.id.color);
		mColorThread = mColorView.getThread();

		setupLocationHandler();
	}

	private void setupLocationHandler() {
		locationProvider = LocationManager.GPS_PROVIDER;
		targetLocation = new Location(locationProvider);
		targetLocation.setLatitude(60.163186);
		targetLocation.setLongitude(10.315984);
		// Acquire a reference to the system Location Manager
		locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		lastKnownLocation = locationManager
				.getLastKnownLocation(locationProvider);
		// Define a listener that responds to location updates
		locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				// Called when a new location is found by the network location
				// provider.
				updatePosition(location);
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}

			public void onProviderEnabled(String provider) {
			}

			public void onProviderDisabled(String provider) {
			}
		};
		// Register the listener with the Location Manager to receive location
		// updates
		locationManager.requestLocationUpdates(locationProvider, 0, 0,
				locationListener);
	}

	private void updatePosition(Location location) {
		// Find distance to target.
		double distance = location.distanceTo(targetLocation);
//		setColor(distance);
		mColorThread.setDistance((int) distance);
	}

	private void setColor(double distance) {
		if (distance < 20) {
			// oransje->r¿dt
			red = 255;
			green = (int) (140.0 * (distance / 20));
			blue = 0;
		} else {
			// gultoner
			red = 255;
			green = 255;
			if (distance >= 255)
				blue = 204;
			else
				blue = (int) (distance - 41);
		}
		mColorThread.setAlpha(alpha);
		mColorThread.setRed(red);
		mColorThread.setGreen(green);
		mColorThread.setBlue(blue);
	}

}
