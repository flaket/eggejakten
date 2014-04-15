package io.flakstad.eggejakten;

import io.flakstad.eggejakten.DistanceView.DistanceThread;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class FinalActivity extends Activity {

	static final String TAG = "FinalActivity";

	private DistanceView mDistanceView;
	private DistanceThread mDistanceThread;
	LocationManager locationManager;
	String locationProvider;
	Location lastKnownLocation, targetLocation;
	LocationListener locationListener;

	final double targetLatitude = 60.163186;
	final double targetLongitude = 10.315984;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_final);
		mDistanceView = (DistanceView) findViewById(R.id.color);
		mDistanceThread = mDistanceView.getThread();
		setupLocationHandler();
	}

	private void setupLocationHandler() {
		locationProvider = LocationManager.GPS_PROVIDER;
		targetLocation = new Location(locationProvider);
		targetLocation.setLatitude(targetLatitude);
		targetLocation.setLongitude(targetLongitude);
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
		// setColor(distance);
		mDistanceThread.setDistance((int) distance);
	}

	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(locationListener);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
