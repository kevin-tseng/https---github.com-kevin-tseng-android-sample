package org.webViewEx;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class WebViewEx extends Activity {
	private EditText etUrl;
	private Button btnSubmit;
	private WebView wvBrowser; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
    }
	private void findViews() {
		etUrl = (EditText)findViewById(R.id.etUrl);
		int start = "http://".length();
		int stop = etUrl.getText().toString().length();
		etUrl.setSelection(start, stop);
		btnSubmit = (Button)findViewById(R.id.btnSubmit);
		btnSubmit.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				String url = etUrl.getText().toString();
				wvBrowser.loadUrl(url);	
			}
		});
		wvBrowser = (WebView) findViewById(R.id.wvBrowser);
		wvBrowser.getSettings().setJavaScriptEnabled(true);
		wvBrowser.loadUrl(getString(R.string.googleUrl));	
		wvBrowser.setWebViewClient(new MyWebViewClient());
	}
	
	private class MyWebViewClient extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        view.loadUrl(url);
	        return true;
	    }
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK) && wvBrowser.canGoBack()) {
	    	wvBrowser.goBack();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}	
}