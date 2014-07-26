package org.myLocTrack;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MyLocTrack extends MapActivity{
	private MapView mView;
	private MapController mControl;
	private LocationManager locMgr;
	private MarkerOverlay myLocMarker;
	private GeoPoint myGP;
	private String best;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initLocMgr();
		findViews();
		initMapView();
	}

	private void initLocMgr() {
		locMgr = (LocationManager) getSystemService(
				Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		best = locMgr.getBestProvider(criteria, true);				
	}

	private void findViews() {
		mView = (MapView) findViewById(R.id.mView);
	}

	private void initMapView() {
		mView.setBuiltInZoomControls(true);
		mView.setTraffic(true);
		mControl = mView.getController();
		mControl.setZoom(15);
		Location myLoc = locMgr.getLastKnownLocation(best);
		if(myLoc != null){
			myGP = locToGP(myLoc);
			mControl.animateTo(myGP);
			addMyLocMarker(myGP);
		}
	}
	
	private GeoPoint locToGP(Location location) {
		int latitudeE6 = (int) (location.getLatitude() * 1E6);
		int longitudeE6 = (int) (location.getLongitude() * 1E6);
		GeoPoint gp = new GeoPoint(latitudeE6, longitudeE6);
		return gp;
	}

	private void addMyLocMarker(GeoPoint myGP) {		
		myLocMarker = new MarkerOverlay(myGP);
		List<Overlay> overlays = mView.getOverlays();
		if(overlays.size() != 0){
			for(int i=0; i<overlays.size(); i++){
				if(overlays.get(i) instanceof MarkerOverlay)
					overlays.remove(i);
			}
		}
		overlays.add(myLocMarker);		
	}
	
	private class MarkerOverlay extends ItemizedOverlay<OverlayItem> {
		private List<OverlayItem> olItems = new ArrayList<OverlayItem>();
		public MarkerOverlay(GeoPoint myGP) {
			super(boundCenterBottom(getResources().getDrawable(
					android.R.drawable.ic_menu_mylocation)));
			olItems.add(new OverlayItem(myGP, "MyLoc", best + " fix"));
			populate();
		}

		@Override
		protected OverlayItem createItem(int i) {
			return olItems.get(i);
		}

		@Override
		public int size() {
			return olItems.size();
		}

		@Override
		protected boolean onTap(int index) {
			Toast.makeText(
					MyLocTrack.this, 
					olItems.get(index).getSnippet(), 
					Toast.LENGTH_SHORT)
					.show();
			return true;
		}
	}

	LocationListener listener = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) {
			myGP = locToGP(location);
			mControl.animateTo(myGP);
			addMyLocMarker(myGP);
		}

		@Override
		public void onProviderDisabled(String provider) {
			Toast.makeText(MyLocTrack.this, 
					"onProviderDisabled", Toast.LENGTH_LONG);
		}

		@Override
		public void onProviderEnabled(String provider) {
			Toast.makeText(MyLocTrack.this, 
					"onProviderEnabled", Toast.LENGTH_LONG);
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			Toast.makeText(MyLocTrack.this, 
					"onStatusChanged", Toast.LENGTH_LONG);
		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		if(locMgr.isProviderEnabled(LocationManager.GPS_PROVIDER))
			best = LocationManager.GPS_PROVIDER;
		locMgr.requestLocationUpdates(best, 10000, 1, listener);	
	}

	@Override
	protected void onPause() {
		super.onPause();
		locMgr.removeUpdates(listener);		
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}