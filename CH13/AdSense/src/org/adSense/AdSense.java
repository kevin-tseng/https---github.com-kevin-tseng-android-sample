package org.adSense;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class AdSense extends Activity {
	private WebView wvAdSense; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
    }
	private void findViews() {
		wvAdSense = (WebView) findViewById(R.id.wvAdSense);
		wvAdSense.getSettings().setJavaScriptEnabled(true);
		wvAdSense.loadUrl("http://ronforwork.appspot.com/mobileAd.html");	
	}
}