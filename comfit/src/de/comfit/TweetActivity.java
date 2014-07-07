package de.comfit;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import android.app.Activity;
import android.os.Bundle;

public class TweetActivity extends Activity {

	@Override
	   protected void onCreate(Bundle savedInstanceState)
	   {
	      super.onCreate(savedInstanceState);
	      new Tweet().execute("blaaaa");
	   }
	
	
}
