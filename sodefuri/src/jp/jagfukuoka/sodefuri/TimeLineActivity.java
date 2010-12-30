package jp.jagfukuoka.sodefuri;

import java.util.ArrayList;
import java.util.List;

import jp.jagfukuoka.sodefuri.preference.TwitterPreferenceManager;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

/**
 * ユーザーのタイムラインを一覧で表示するActivity
 * 
 * @author shikajiro
 * 
 */
public class TimeLineActivity extends ListActivity {
	private TwitterPreferenceManager tpm = new TwitterPreferenceManager(this);  

	private static final int FOLLOW = 1;
	private static final CharSequence FOLLOW_LABEL = "フォロー";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// timeline取得処理
		String screenName = getIntent().getStringExtra("screen_name");
		List<String> list = this.getTimeLine(screenName);

		setListAdapter(new ArrayAdapter<String>(this, R.layout.timeline_item,list));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, FOLLOW, 0, FOLLOW_LABEL);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case FOLLOW:
			ConfigurationBuilder builder = new ConfigurationBuilder();
			Configuration conf = builder.setOAuthAccessToken(tpm.getAccessToken())
			.setOAuthAccessTokenSecret(tpm.getAccessTokenSercret())
			.setOAuthConsumerKey(TwitterPreferenceManager.CONSUMER_KEY)
			.setOAuthConsumerSecret(TwitterPreferenceManager.CONSUMER_SERCRET)
			.setDebugEnabled(true)
			.build();
			Twitter twitter = new TwitterFactory(conf).getInstance();
			try {
				String screen_name = getIntent().getStringExtra("screen_name");
				twitter.createFriendship(screen_name);
				Toast.makeText(getApplicationContext(), "フォローしました。", Toast.LENGTH_LONG).show();
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 指定したユーザーのタイムラインを取得
	 * 
	 * @param screenName
	 * @return
	 */
	private List<String> getTimeLine(String screenName) {
		List<String> result = new ArrayList<String>();

		Twitter twitter = new TwitterFactory().getInstance();
		ResponseList<Status> userTimeline;
		try {
			userTimeline = twitter.getUserTimeline(screenName);
			for (Status status : userTimeline) {
				result.add(status.getText());
			}
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
