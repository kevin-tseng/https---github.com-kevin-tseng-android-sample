package org.myLoc;

import android.os.Bundle;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class MyLoc extends MapActivity {
	private MapView mView;
	private MapController mControl;
	private MyLocationOverlay mlOverlay;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		findViews();
		initMapView();
	}

	private void findViews() {
		mView = (MapView) findViewById(R.id.mView);
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