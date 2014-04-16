package io.flakstad.eggejakten;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FifthActivity extends Activity {
	static final String TAG = "FifthActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fifth);
		final Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				final TextView password = (TextView) findViewById(R.id.text);
				String pass = password.getText().toString();

				if (pass.toLowerCase().equals(getString(R.string.fifthPassword))
						|| pass.equals(getString(R.string.hackPassword))) {
					startActivity(new Intent(FifthActivity.this,
							SixthActivity.class));
				} else
					Toast.makeText(FifthActivity.this, "Feil kodeord.",
							Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
