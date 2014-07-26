package org.intentBundle;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import java.text.NumberFormat;
public class Result extends Activity {
    private TextView tvSum;
    private TextView tvAverage;
    private TextView tvResult;
    private Button btnBack;
    @Override
    public void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.result);
    	findViews();
    	try{
    		showResults();
    	}catch(NumberFormatException nfe){
    		tvResult.setText(getString(R.string.inputError));
    	}
    }

	private void findViews(){
		tvSum = (TextView)findViewById(R.id.tvSum);
		tvAverage = (TextView)findViewById(R.id.tvAverage);
		tvResult = (TextView)findViewById(R.id.tvResult);
		btnBack = (Button)findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Result.this.finish();
			}
		});
    }

    private void showResults(){
    	NumberFormat nf = NumberFormat.getInstance();
    	nf.setMaximumFractionDigits(2);
    	Bundle bundle = this.getIntent().getExtras();
    	double chinese = Double.parseDouble(
    					 bundle.getString("chinese"));
    	double english = Double.parseDouble(
    					 bundle.getString("english"));    	
    	double math = Double.parseDouble(
    					 bundle.getString("math"));
    	double sum = chinese + english + math;
    	double average = sum/3;
    	tvSum.setText(getString(R.string.totalScores) + nf.format(sum));
    	tvAverage.setText(getString(R.string.average) + nf.format(average));      	
    	if(average >= 85)
    		tvResult.setText(getString(R.string.top));
    	else if(average >= 75)
    		tvResult.setText(getString(R.string.high));
    	else
    		tvResult.setText(getString(R.string.general));
    }
}
