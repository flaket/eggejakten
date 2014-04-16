package io.flakstad.eggejakten;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ThirdActivity extends Activity {
	static final String TAG = "ThirdActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_third);
		final Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				final TextView password = (TextView) findViewById(R.id.text);
				String pass = password.getText().toString();

				if (pass.toLowerCase().equals(getString(R.string.thirdPassword))
						|| pass.equals(getString(R.string.hackPassword))) {
					startActivity(new Intent(ThirdActivity.this,
							FourthActivity.class));
				} else
					Toast.makeText(ThirdActivity.this, "Feil kodeord.",
							Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}