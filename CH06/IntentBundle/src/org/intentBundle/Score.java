package org.intentBundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
public class Score extends Activity {
    private EditText etChinese;
    private EditText etEnglish;
    private EditText etMath;
    private Button btnSubmit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
    }

	private void findViews(){
    	etChinese = (EditText)findViewById(R.id.etChinese);
    	etEnglish = (EditText)findViewById(R.id.etEnglish);
    	etMath = (EditText)findViewById(R.id.etMath);
    	btnSubmit = (Button)findViewById(R.id.btnSubmit);
    	btnSubmit.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
	            intent.setClass(Score.this, Result.class);
	            Bundle bundle = new Bundle();
	            bundle.putString("chinese", etChinese.getText().toString());
	            bundle.putString("english", etEnglish.getText().toString());
	            bundle.putString("math", etMath.getText().toString());
	            intent.putExtras(bundle);
	            startActivity(intent);	
			}
		});
    } 
}