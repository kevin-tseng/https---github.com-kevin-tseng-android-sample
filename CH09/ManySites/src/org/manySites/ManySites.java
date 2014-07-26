package org.manySites;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class ManySites extends MapActivity {
	private MapView mView;
	private MapController mControl;
	private MyLocationOverlay mlOverlay;
	GeoPoint yangmingshan;
	GeoPoint yushan;
	GeoPoint taroko;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		findViews();
		initGeoPoint();
		initMapView();
	}

	private void findViews() {
		mView = (MapView) findViewById(R.id.mView);
	}
	
	private void initGeoPoint() {
		yangmingshan = new GeoPoint(
				(int)(25.091075 * 1E6), (int)(121.559834 * 1E6));
		yushan = new GeoPoint(
				(int)(23.791952 * 1E6), (int)(120.861379 * 1E6));
		taroko = new GeoPoint(
				(int)(24.151287 * 1E6), (int)(121.625537 * 1E6));
	}

	private void initMapView() {
		mView.setBuiltInZoomControls(true);
		mView.setTraffic(true);
		mlOverlay = new MyLocationOverlay(this, mView);
		mControl = mView.getController();
		mlOverlay.runOnFirstFix(new Runnable() {
			public void run() {
				mControl.setZoom(15);
				mControl.animateTo(mlOverlay.getMyLocation());
			}
		});
		mView.getOverlays().add(mlOverlay);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mymenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.yangmingshan:
			mControl.animateTo(yangmingshan);
			return true;
		case R.id.yushan:
			mControl.animateTo(yushan);
			return true;
		case R.id.taroko:
			mControl.animateTo(taroko);
			return true;
		case R.id.myloc:
			mControl.animateTo(mlOverlay.getMyLocation());
			return true;
		case R.id.exit:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		mlOverlay.enableMyLocation();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mlOverlay.disableMyLocation();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}