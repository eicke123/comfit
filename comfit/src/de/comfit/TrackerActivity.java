package de.comfit;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;
import android.location.Location;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;
 
public class TrackerActivity extends MapActivity implements LocationListener {
	/** Called when the activity is first created. */
//	
//	private Intent i;
//	private static Context mContext;
//	
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.main);
//		mContext = this;
//		
//		initializeGPSTracker();
//		
////		clickStartTracking();
//	}
//	
//	private void clickStartTracking() {
//		// use this to start and trigger a service
//		i= new Intent(this, GPSTracker.class);
//		// potentially add data to the intent
//		i.putExtra("KEY1", "Value to be used by the service");
//		
//		mContext.startService(i); 
//	}
//	
//	private void clickStopTracking() {
//		mContext.stopService(i);
//	}
// 
//	@Override
//	protected boolean isRouteDisplayed() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//	
//	private void initializeGPSTracker() {
//		GPSTracker gps = new GPSTracker(this);
//		if(gps.canGetLocation()){ // gps enabled} // return boolean true/false
//			double latitude = gps.getLatitude(); // returns latitude
//			double longitude = gps.getLongitude(); // returns longitude
//			//gps.showSettingsAlert();
//			Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show(); 
//
//			gps.stopUsingGPS();
//		}
//	}
	
	LocationManager locman;
	LocationListener loclis;
	Location Location;
	private MapView map;

	List<GeoPoint> geoPointsArray = new ArrayList<GeoPoint>();
	private MapController controller;
	String provider = LocationManager.GPS_PROVIDER;
	double lat;
	double lon;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    initMapView();
	    initMyLocation();
	    locman = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    // locman.requestLocationUpdates(provider,60000, 100,loclis);
	    // Location = locman.getLastKnownLocation(provider);

	}

	/** Find and initialize the map view. */
	private void initMapView() {
	    map = (MapView) findViewById(R.id.map_view);
	    controller = map.getController();
	    map.setSatellite(false);
	    map.setBuiltInZoomControls(true);
	}

	/** Find Current Position on Map. */
	private void initMyLocation() {
	    final MyLocationOverlay overlay = new MyLocationOverlay(this, map);
	    overlay.enableMyLocation();
	    overlay.enableCompass(); // does not work in emulator
	    overlay.runOnFirstFix(new Runnable() {
	        public void run() {
	            // Zoom in to current location
	            controller.setZoom(24);
	            controller.animateTo(overlay.getMyLocation());
	        }
	    });
	    map.getOverlays().add(overlay);
	}

	@Override
	public void onLocationChanged(Location location) {
	    if (Location != null) {
	        lat = Location.getLatitude();
	        lon = Location.getLongitude();
	        GeoPoint New_geopoint = new GeoPoint((int) (lat * 1e6),
	                (int) (lon * 1e6));
	        controller.animateTo(New_geopoint);

	    }

	}

	class MyOverlay extends Overlay {
	    public MyOverlay() {
	    }

	    public void draw(Canvas canvas, MapView mapv, boolean shadow) {
	        super.draw(canvas, mapv, shadow);

	        Projection projection = map.getProjection();
	        Path p = new Path();
	        for (int i = 0; i < geoPointsArray.size(); i++) {
	            if (i == geoPointsArray.size() - 1) {
	                break;
	            }
	            Point from = new Point();
	            Point to = new Point();
	            projection.toPixels(geoPointsArray.get(i), from);
	            projection.toPixels(geoPointsArray.get(i + 1), to);
	            p.moveTo(from.x, from.y);
	            p.lineTo(to.x, to.y);
	        }
	        Paint mPaint = new Paint();
	        mPaint.setStyle(Style.STROKE);
	        mPaint.setColor(0xFFFF0000);
	        mPaint.setAntiAlias(true);
	        canvas.drawPath(p, mPaint);
	        super.draw(canvas, map, shadow);
	    }
	}

	@Override
	public void onProviderDisabled(String provider) {
	    // TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
	    // TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	    // TODO Auto-generated method stub

	}

	@Override
	protected boolean isRouteDisplayed() {
	    // TODO Auto-generated method stub
	    return false;
	}
}
