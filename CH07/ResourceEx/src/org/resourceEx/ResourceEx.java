package org.resourceEx;

import android.app.Activity;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ResourceEx extends Activity {
	private TextView tvGetText;
	private TextView tvGetString;
	private TextView tvRes;
	private ImageView ivPhoto;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		findViews();
	}

	private void findViews() {
		tvGetText = (TextView) findViewById(R.id.tvGetText);
		tvGetString = (TextView) findViewById(R.id.tvGetString);
		tvRes = (TextView) findViewById(R.id.tvRes);
		ivPhoto = (ImageView)findViewById(R.id.ivPhoto);		
		CharSequence cs;
		String str;		
		cs = getText(R.string.styled_text);
		tvGetText.setText(cs);
		
		str = getString(R.string.styled_text);
		tvGetString.setText(str);
		
		Resources res = getResources();		
		cs = res.getText(R.string.styled_text);
		tvRes.setText(cs);		
		ivPhoto.setImageDrawable(res.getDrawable(R.drawable.photo));		
		MediaPlayer mp = MediaPlayer.create(ResourceEx.this, R.raw.ring);
		mp.start();
	}
}
