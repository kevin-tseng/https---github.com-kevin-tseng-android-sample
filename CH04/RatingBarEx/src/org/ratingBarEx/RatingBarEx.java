package org.ratingBarEx;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;

public class RatingBarEx extends Activity {
	private RatingBar rtbBook;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
    }
	private void findViews() {
		rtbBook	= (RatingBar)findViewById(R.id.rtbBook);
		rtbBook.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {			
			@Override
			public void onRatingChanged(RatingBar ratingBar, 
					float rating, boolean fromUser) {
				String msg = getString(R.string.start_number) + rating;
				Toast.makeText(RatingBarEx.this, msg, Toast.LENGTH_SHORT).show();
			}
		});
	}
}