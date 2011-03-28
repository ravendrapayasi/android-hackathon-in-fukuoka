package jp.jagfukuoka.sodefuri.twitter;

import java.util.ArrayList;
import java.util.List;

import jp.jagfukuoka.sodefuri.preference.TwitterPreferences;
import twitter4j.ProfileImage;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class TwitterRequest {
	
	public static String getImageUrl(Context context, String str){
		String image = "";
		ConfigurationBuilder builder = new ConfigurationBuilder();
		Configuration conf = builder
				.setOAuthAccessToken(TwitterPreferences.getAccessToken(context))
				.setOAuthAccessTokenSecret(TwitterPreferences.getAccessTokenSercret(context))
				.setOAuthConsumerKey(TwitterPreferences.CONSUMER_KEY)
				.setOAuthConsumerSecret(
						TwitterPreferences.CONSUMER_SERCRET)
				.setDebugEnabled(true).build();
		Twitter twitter = new TwitterFactory(conf).getInstance();
		try {
			ProfileImage profileImage = twitter.getProfileImage(str, ProfileImage.NORMAL);
			image = profileImage.getURL();
			Log.d("image", image);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return image;
	}

	public static void createFriendship(Context context, String screen_name) {
		ConfigurationBuilder builder = new ConfigurationBuilder();
		Configuration conf = builder.setOAuthAccessToken(TwitterPreferences.getAccessToken(context))
		.setOAuthAccessTokenSecret(TwitterPreferences.getAccessTokenSercret(context))
		.setOAuthConsumerKey(TwitterPreferences.CONSUMER_KEY)
		.setOAuthConsumerSecret(TwitterPreferences.CONSUMER_SERCRET)
		.setDebugEnabled(true)
		.build();
		Twitter twitter = new TwitterFactory(conf).getInstance();
		try {
			twitter.createFriendship(screen_name);
			Toast.makeText(context, "フォローしました。", Toast.LENGTH_LONG).show();
		} catch (TwitterException e) {
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}

	/**
	 * 指定したユーザーのタイムラインを取得
	 * 
	 * @param screenName
	 * @return
	 */
	public static List<String> getUserTimeline(String screenName) {
		List<String> result = new ArrayList<String>();
		Twitter twitter = new TwitterFactory().getInstance();
		try {
			ResponseList<Status> userTimeline = twitter.getUserTimeline(screenName);
			for (Status status : userTimeline) {
				result.add(status.getText());
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return result;
	}

}
