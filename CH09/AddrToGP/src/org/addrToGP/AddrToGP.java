package org.addrToGP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class AddrToGP extends MapActivity{
	private Button btnSubmit;
	private EditText etAddress;
	private MapView mView;
	private MapController mControl;
	private MyLocationOverlay mlOverlay;
	private GeoPoint gp;
	private String input;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		findViews();
		initMapView();
	}
	
	OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			input = etAddress.getText().toString().trim();
			if (!input.equals("")) {
				Geocoder geocoder = new Geocoder(AddrToGP.this);
				List<Address> addresses = null;
				Address address = null;
				try {
					addresses = geocoder.getFromLocationName(input, 1);
				} catch (IOException e) {
					Log.e("AddrToGP", e.toString());
				}
				if(addresses == null || addresses.isEmpty()){
					Toast.makeText(AddrToGP.this, R.string.addressNotFound, 
							Toast.LENGTH_SHORT).show();
				}else{
					address = addresses.get(0);
					double geoLatitude = address.getLatitude() * 1E6;
					double geoLongitude = address.getLongitude() * 1E6;
					gp = new GeoPoint((int) geoLatitude, (int) geoLongitude);
					mControl.animateTo(gp);
					addMyLocMarker(gp);
				}
			}else {
	            Toast.makeText(AddrToGP.this, R.string.addressIsEmpty,
	            		Toast.LENGTH_SHORT).show();
	        }		
		}
	};
	
	private void findViews() {
		btnSubmit = (Button) findViewById(R.id.btnSubmit);
		btnSubmit.setOnClickListener(listener);
		etAddress = (EditText) findViewById(R.id.etAddress);
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

	private void addMyLocMarker(GeoPoint myGP) {		
		MarkerOverlay locMarker = new MarkerOverlay(myGP);
		List<Overlay> overlays = mView.getOverlays();
		if(overlays.size() != 0){
			for(int i=0; i<overlays.size(); i++){
				if(overlays.get(i) instanceof MarkerOverlay)
					overlays.remove(i);
			}
		}
		overlays.add(locMarker);		
	}
	
	private class MarkerOverlay extends ItemizedOverlay<OverlayItem> {
		private List<OverlayItem> olItems = new ArrayList<OverlayItem>();
		public MarkerOverlay(GeoPoint myGP) {
			super(boundCenterBottom(getResources().getDrawable(
					android.R.drawable.ic_menu_myplaces)));
			olItems.add(new OverlayItem(myGP, "我的位置", input));
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
					AddrToGP.this, 
					olItems.get(index).getSnippet(), 
					Toast.LENGTH_SHORT)
					.show();
			return true;
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