package org.tweenAnimEx;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.widget.AdapterView.OnItemSelectedListener;

public class TweenAnimEx extends Activity {
	private EditText etUserName;
	private Button btnSubmit;
	private Spinner spInter;
	private TextView tvAnim;
	private ViewFlipper fpText;
	private Spinner spAnim;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews_anim01();
        findViews_anim02();
        findViews_anim03();
    }

	private void findViews_anim01() {
		etUserName = (EditText)findViewById(R.id.etUserName);
		btnSubmit = (Button)findViewById(R.id.btnSubmit);		
		btnSubmit.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				 Animation anim = AnimationUtils.loadAnimation(
						 TweenAnimEx.this, R.anim.anim_edittext);
			     etUserName.startAnimation(anim);				
			}
		});		
	}

	private void findViews_anim02() {		
		tvAnim = (TextView)findViewById(R.id.tvAnim);
		spInter = (Spinner)findViewById(R.id.spInter);
		spInter.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView parent,
			        View view, int pos, long id) {
				View parentView = (View)tvAnim.getParent();
				TranslateAnimation anim = new TranslateAnimation( 
					0.0f, 
					parentView.getWidth()-parentView.getPaddingLeft()-
					parentView.getPaddingRight()-tvAnim.getWidth(), 
					0.0f, 0.0f);
		        anim.setDuration(1000); 
		        anim.setStartOffset(300); 
		        anim.setRepeatMode(Animation.RESTART); 
		        anim.setRepeatCount(Animation.INFINITE); 
		        
		        int inter_id = android.R.anim.accelerate_interpolator; 		        
		        switch (pos) {		        	
		            case 0:
		            	inter_id = android.R.anim.accelerate_interpolator;
		                break;
		            case 1:
		            	inter_id = android.R.anim.decelerate_interpolator;
		                break;
		            case 2:
		            	inter_id = android.R.anim.accelerate_decelerate_interpolator;
		                break;
		            case 3:
		            	inter_id = android.R.anim.anticipate_interpolator;
		                break;
		            case 4:
		            	inter_id = android.R.anim.overshoot_interpolator;
		                break;
		            case 5:
		            	inter_id = android.R.anim.anticipate_overshoot_interpolator;
		                break;
		            case 6:
		            	inter_id = android.R.anim.bounce_interpolator;
		                break;
		        }
		        anim.setInterpolator(AnimationUtils.loadInterpolator(
		        		TweenAnimEx.this, inter_id));
		        tvAnim.startAnimation(anim);
		    }
			
			@Override
		    public void onNothingSelected(AdapterView parent) {}
		});	
	}

	private void findViews_anim03() {
		fpText = (ViewFlipper)findViewById(R.id.fpText);
		fpText.startFlipping();
		spAnim = (Spinner)findViewById(R.id.spAnim);
		spAnim.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView parent,
			        View view, int pos, long id) {
						        
		        int anim_in = R.anim.translate_up_in;
		        int anim_out = R.anim.translate_up_out; 
		        switch (pos) {		        	
		            case 0:
		            	anim_in = R.anim.translate_up_in;
		            	anim_out = R.anim.translate_up_out;
		                break;
		            case 1:
		            	anim_in = R.anim.translate_left_in;
		            	anim_out = R.anim.translate_left_out;
		                break;
		            case 2:
		            	anim_in = android.R.anim.fade_in;
		            	anim_out = android.R.anim.fade_out;
		                break;
		            case 3:
		            	anim_in = R.anim.abstract_in;
		            	anim_out = R.anim.abstract_out;
		                break;		            
		        }
		        fpText.setInAnimation(AnimationUtils.loadAnimation(
		        		TweenAnimEx.this, anim_in));
		        fpText.setOutAnimation(AnimationUtils.loadAnimation(
		        		TweenAnimEx.this, anim_out));
		    }
			
			@Override
		    public void onNothingSelected(AdapterView parent) {}
		});				
	}
}