package de.comfit;

import android.app.Activity;
import android.os.Bundle;

public class TweetActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		String tweetMessage = getIntent().getStringExtra("message");
		new Tweet().execute(tweetMessage);
		finish();
	}
}
