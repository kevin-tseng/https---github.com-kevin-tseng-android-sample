package org.marker;

import java.util.ArrayList;
import java.util.List;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.OverlayItem;

public class Marker extends MapActivity {
	private MapView mView;
	private MapController mControl;
	private MyLocationOverlay mlOverlay;
	private MarkerOverlay markOverlay;
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
		initMarkerOverlay();
	}
	
	private void findViews() {
		mView = (MapView) findViewById(R.id.mView);
	}

	private void initGeoPoint() {
		yangmingshan = new GeoPoint((int) (25.091075 * 1E6),
				(int) (121.559834 * 1E6));
		yushan = new GeoPoint(
				(int) (23.791952 * 1E6), (int) (120.861379 * 1E6));
		taroko = new GeoPoint(
				(int) (24.151287 * 1E6), (int) (121.625537 * 1E6));
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

	private class MarkerOverlay extends ItemizedOverlay<OverlayItem> {
		private List<OverlayItem> olItems = new ArrayList<OverlayItem>();
		public MarkerOverlay(Drawable defaultMarker) {
			super(boundCenterBottom(defaultMarker));
			olItems.add(new OverlayItem(yangmingshan, "陽明山", "陽明山公園"));
			olItems.add(new OverlayItem(yushan, "玉山", "玉山國家公園"));
			olItems.add(new OverlayItem(taroko, "太魯閣", "太魯閣國家公園"));
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
					Marker.this, 
					olItems.get(index).getSnippet(), 
					Toast.LENGTH_SHORT)
					.show();
			return true;
		}
	}
	
	private void initMarkerOverlay() {
		Drawable marker = getResources().getDrawable(
				android.R.drawable.ic_menu_myplaces);
		markOverlay = new MarkerOverlay(marker);
		mView.getOverlays().add(markOverlay);		
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
			System.exit(0);
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