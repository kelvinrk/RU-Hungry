package com.example.myfood;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.example.model.MyMarker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Activity {

	static final LatLng Busch = new LatLng(40.522060, -74.458184);
	static final LatLng Livingston = new LatLng(40.522362, -74.434731);
	static final LatLng CAC = new LatLng(40.500037, -74.447799);
	static final LatLng Cook = new LatLng(40.479139, -74.428579);

	private GoogleMap map;
	private Bundle Bundle1;
	private double latitude;
	private double longitude;
	private ArrayList<String> arraylist = new ArrayList<String>();
	private HashMap<String, Integer> mMarkers = new HashMap<String, Integer>();
	private ArrayList<MyMarker> mMyMarkersArray = new ArrayList<MyMarker>();

	private HashMap<Marker, MyMarker> mMarkersHashMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v("Test", "MapActivity()");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		Bundle1 = this.getIntent().getExtras();
		arraylist = Bundle1.getStringArrayList("ResMap");

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		map.setMyLocationEnabled(true);

		mMarkersHashMap = new HashMap<Marker, MyMarker>();
		for (int i = 0; i < arraylist.size(); i++) {

			String[] temp = arraylist.get(i).toString().split(",");
			mMyMarkersArray.add(new MyMarker(Integer.parseInt(temp[0]), Integer
					.parseInt(temp[1]), temp[2], Double.parseDouble(temp[3]),
					Double.parseDouble(temp[4])));

		}

		// plot markers
		if (mMyMarkersArray.size() > 0) {
			for (MyMarker myMarker : mMyMarkersArray) {

				// Create user marker with custom icon and other options
				MarkerOptions markerOption = new MarkerOptions()
						.position(
								new LatLng(myMarker.getmLatitude(), myMarker
										.getmLongitude()))
						.title(myMarker.getmTitle())
						.snippet("Tap to see their menus!");

				Marker currentMarker = map.addMarker(markerOption);
				mMarkers.put(currentMarker.getId(), myMarker.getmId());
				mMarkersHashMap.put(currentMarker, myMarker);
			}
		}

		LocationManager locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				if (location != null) {

					latitude = location.getLatitude();
					longitude = location.getLongitude();

				}

			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub

			}
		};
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				400, 1000, locationListener);
		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 400, 1000, locationListener);

		switch (mMyMarkersArray.get(0).getm_area_id()) {
		case 1:
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(CAC, 16));
			break;
		case 2:
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(Busch, 16));
			break;
		case 3:
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(Livingston, 16));
			break;
		case 4:
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(Cook, 16));
			break;
		default:
			Log.v("gps", String.valueOf(latitude));
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					40.522713, -74.460597), 16));
			break;

		}

		map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
			public void onInfoWindowClick(Marker marker) {
				Intent Intent1 = new Intent();
				Intent1.setClass(MapActivity.this, MenuActivity.class);
				Bundle Bundle2 = new Bundle();
				Bundle2.putInt("id", mMarkers.get(marker.getId()));
				Intent1.putExtras(Bundle2);
				startActivity(Intent1);
			}
		});
	}

	@Override
	protected void onRestart() {
		super.onRestart();

	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	protected void onPause() {
		super.onPause();

	}

	@Override
	protected void onStop() {
		super.onStop();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		finish();
	}

}
