package org.contextMenuEx;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ContextMenuEx extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		LinearLayout linear = (LinearLayout)findViewById(R.id.linear);
		registerForContextMenu(linear);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
									ContextMenuInfo menuInfo) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mymenu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item){
		String msg = "";
		switch (item.getItemId()) {
		case R.id.yangmingshan:
			msg = getString(R.string.yangmingshan);
			break;
		case R.id.yushan:
			msg = getString(R.string.yushan);
			break;
		case R.id.taroko:
			msg = getString(R.string.taroko);
			break;
		case R.id.myloc:
			msg = getString(R.string.myloc);
			break;
		case R.id.exit:
			this.finish();
		default:
			return super.onContextItemSelected(item);
		}
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		return true;
	}
}