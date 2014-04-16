package io.flakstad.eggejakten;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FourthActivity extends Activity {
	static final String TAG = "FourthActivity";

	MediaPlayer mp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fourth);
		mp = MediaPlayer.create(getApplicationContext(), R.raw.fourth_sting);
		final Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				final TextView password = (TextView) findViewById(R.id.text);
				String pass = password.getText().toString();

				if (pass.toLowerCase().equals(
						getString(R.string.fourthPassword))
						|| pass.equals(getString(R.string.hackPassword))) {
					startActivity(new Intent(FourthActivity.this,
							FifthActivity.class));
				} else
					Toast.makeText(FourthActivity.this, "Feil kodeord.",
							Toast.LENGTH_LONG).show();
			}
		});
		final Button playButton = (Button) findViewById(R.id.button_play);
		playButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				mp.start();
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mp.release();
		mp = null;
	}

	@Override
	protected void onResume() {
		super.onResume();
		mp = MediaPlayer.create(getApplicationContext(), R.raw.fourth_sting);
	}
}
