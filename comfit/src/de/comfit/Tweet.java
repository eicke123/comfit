package de.comfit;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import android.os.AsyncTask;

/**
 * Class for sharing a Message on Twitter
 * 
 * @author Comtec
 * 
 */
public class Tweet extends AsyncTask<String, Object, Object> {

	@Override
	protected Object doInBackground(String... arg0) {
		sendMessageToTwitter(arg0[0]);
		return null;
	}

	public void sendMessageToTwitter(String message) {
		// Your Twitter App's Consumer Key
		String consumerKey = "gcyUYUX10zQqYvoQKbi4xq7ys";

		// Your Twitter App's Consumer Secret
		String consumerSecret = "rlIrtN8lbwur0wdVenhXoiq5MZdJkwiaLSTQhsnpI6UdTbsWGb";

		// Your Twitter Access Token
		String accessToken = "2609196686-GBA7rYGm8WPIIxn8HYUIz1RfF1fqFWJglLUubUY";

		// Your Twitter Access Token Secret
		String accessTokenSecret = "owr3VXye2cv73SKwAJXACOeXiYfulK4NYcX9LKjsQmJiC";

		// Instantiate a re-usable and thread-safe factory
		TwitterFactory twitterFactory = new TwitterFactory();

		// Instantiate a new Twitter instance
		Twitter twitter = twitterFactory.getInstance();

		// setup OAuth Consumer Credentials
		twitter.setOAuthConsumer(consumerKey, consumerSecret);

		// setup OAuth Access Token
		twitter.setOAuthAccessToken(new AccessToken(accessToken,
				accessTokenSecret));

		// Instantiate and initialize a new twitter status update
		StatusUpdate statusUpdate = new StatusUpdate(message);

		try {
			twitter.updateStatus(statusUpdate);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
