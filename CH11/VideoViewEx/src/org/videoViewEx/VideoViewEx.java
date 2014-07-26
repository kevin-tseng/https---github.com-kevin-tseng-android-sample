package org.videoViewEx;

import android.app.Activity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoViewEx extends Activity {
	//播放SD卡的video檔案
	private String path = "/sdcard/littleMonster.3gp"; 
	//播放網路的video
	//private String path = 
	//	"http://sites.google.com/site/ronforwork/Home/android-2/littleMonster.3gp";
	private VideoView vvScreen;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		findViews();
		playVideo();
	}

	private void findViews() {
		vvScreen = (VideoView) findViewById(R.id.vvScreen);
	}
	
	private void playVideo() {
		vvScreen.setVideoPath(path);
		MediaController mController = new MediaController(this);
		vvScreen.setMediaController(mController);
		vvScreen.requestFocus();
		vvScreen.start();
	}
}