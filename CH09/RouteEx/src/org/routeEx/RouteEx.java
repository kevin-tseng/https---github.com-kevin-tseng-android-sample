package org.routeEx;

import java.io.IOException;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;

public class RouteEx extends MapActivity{
	private Button btnRoute;
	private EditText etAddress;
	private LocationManager locMgr;
	private String best;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initLocMgr(); 
		setContentView(R.layout.main);
		findViews();
	}
	 
	private void initLocMgr() {
		locMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		best = locMgr.getBestProvider(criteria, true);
	}
	
	private void findViews() {
		etAddress = (EditText) findViewById(R.id.etAddress);
		btnRoute = (Button) findViewById(R.id.btnRoute);		
		btnRoute.setOnClickListener(new OnClickListener() {			
			@Override //按下「導航」按鈕
			public void onClick(View v) {
				GeoPoint myGP = null;
				GeoPoint destGP = null;				
				Location myLoc = locMgr.getLastKnownLocation(best); 
				if (myLoc != null) {
					myGP = locToGP(myLoc);
				} else{
					Toast.makeText(RouteEx.this, 
							R.string.lastLocNotFound,
							Toast.LENGTH_SHORT).show();
					return;
				}				
				
				String addr = etAddress.getText().toString().trim();
				if(!addr.equals("")){
					destGP = addrToGP(addr);
				} else{
					Toast.makeText(RouteEx.this, 
							R.string.addressIsEmpty, 
							Toast.LENGTH_SHORT).show();
					return;					
				}
				
				route(myGP, destGP); 			
			}
		});		
	}
	
	//將傳入的Location解析成GeoPoint物件 
	private GeoPoint locToGP(Location location) {
		int latitudeE6 = (int) (location.getLatitude() * 1E6);
		int longitudeE6 = (int) (location.getLongitude() * 1E6);
		GeoPoint gp = new GeoPoint(latitudeE6, longitudeE6);
		return gp;
	}
		
	//將傳入的地址解析成GeoPoint物件
	private GeoPoint addrToGP(String addr) {
		Geocoder geocoder = new Geocoder(this);
		List<Address> addresses = null;
		Address address = null;
		GeoPoint gp = null;
		try {
			addresses = geocoder.getFromLocationName(addr, 1);
		} catch (IOException e) {
			Log.e("RouteEx", e.toString());
		}
		if (addresses == null || addresses.isEmpty()) {
			Toast.makeText(this, R.string.addressNotFound,
					Toast.LENGTH_SHORT).show();
		} else {
			address = addresses.get(0);
			double geoLatitude = address.getLatitude() * 1E6;
			double geoLongitude = address.getLongitude() * 1E6;
			gp = new GeoPoint((int) geoLatitude, (int) geoLongitude);
		}
		return gp;
	}

	//開啟Google地圖伺服器的導航功能
	public void route(GeoPoint fromGP, GeoPoint destGP) {
		String fromGPStr = String.valueOf(fromGP.getLatitudeE6() / 1E6) + ","
				+ String.valueOf(fromGP.getLongitudeE6() / 1E6);
		String destGPStr = String.valueOf(destGP.getLatitudeE6() / 1E6) + ","
				+ String.valueOf(destGP.getLongitudeE6() / 1E6);
		Uri uri = Uri.parse("http://maps.google.com/maps?f=d&saddr="
				+ fromGPStr + "&daddr=" + destGPStr + "&hl=tw");
		Intent intent = new Intent();
		intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.setData(uri);
		startActivity(intent);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}