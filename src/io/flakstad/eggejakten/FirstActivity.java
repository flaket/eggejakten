package io.flakstad.eggejakten;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FirstActivity extends Activity {

	static final String TAG = "FirstActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first);
		final Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.d(TAG, "Starting second activity.");
				startActivity(new Intent(FirstActivity.this,
						SecondActivity.class));
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
