package org.ScrollViewEx;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ScrollViewEx extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        LinearLayout linear = (LinearLayout)findViewById(R.id.linear);
        for (int i=1; i<=20; i++) {
            EditText editText = new EditText(this);
            editText.setHint(getString(R.string.editText) + i);
            LinearLayout.LayoutParams params = 
            	new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            linear.addView(editText, params);

            Button button = new Button(this);
            button.setText(getString(R.string.button) + i);
            linear.addView(button, params);
        }
    }
}