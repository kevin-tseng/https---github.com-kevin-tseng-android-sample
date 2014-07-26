package org.submenuEx;

import org.submenuEx.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class SubmenuEx extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mymenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		String msg = "";
		switch (item.getItemId()) {
		case R.id.yangmingshan:
			msg = getString(R.string.yangmingshan);
			break;
		case R.id.guide:
			msg = getString(R.string.yangmingshan) + " > " + 
					getString(R.string.guide);
			break;
		case R.id.traffic:
			msg = getString(R.string.yangmingshan) + " > " + 
					getString(R.string.traffic);
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
			return super.onOptionsItemSelected(item);
		}
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		return true;
	}
}