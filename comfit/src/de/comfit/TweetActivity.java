package de.comfit;

import android.app.Activity;
import android.os.Bundle;

/**
 * Class for sharing a Message on Twitter
 * @author Comtec
 *
 */
public class TweetActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Message that will be displayed on Twitter!
		String tweetMessage = getIntent().getStringExtra("message");
		new Tweet().execute(tweetMessage);
		finish();
	}
}
