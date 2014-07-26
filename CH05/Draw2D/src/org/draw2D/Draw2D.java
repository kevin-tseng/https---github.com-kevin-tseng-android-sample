package org.draw2D;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Draw2D extends Activity {
	private Button btnSubmit;
	private GeometricView geomView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		findViews();
		
		//GeometricView geomView = new GeometricView(this);
		//setContentView(geomView);
	}
	public void findViews(){
		btnSubmit = (Button)findViewById(R.id.btnSubmit);
		btnSubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				geomView.invalidate();
			}
		});
		geomView = (GeometricView)findViewById(R.id.geomView);
	}
}