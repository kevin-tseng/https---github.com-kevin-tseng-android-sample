package org.assetEx;

import java.io.IOException;
import java.io.InputStream;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class AssetEx extends Activity {
    private TextView tvAsset;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
    }

	private void findViews() {
		tvAsset = (TextView)findViewById(R.id.tvAsset);
        try {
            InputStream is = getAssets().open("dearJohn.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String text = new String(buffer);
            tvAsset.setText(text);
        } catch (IOException e) {
            Log.e("AssetEx", e.toString());
        }		
	}
}