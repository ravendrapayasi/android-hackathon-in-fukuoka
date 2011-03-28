package jp.jagfukuoka.sodefuri;

import java.util.List;

import jp.jagfukuoka.sodefuri.server.twitter.TwitterRequest;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

/**
 * ユーザーのタイムラインを一覧で表示するActivity
 * 
 * @author shikajiro
 * 
 */
public class TimeLineActivity extends ListActivity {

	private static final int FOLLOW = 1;
	private static final CharSequence FOLLOW_LABEL = "フォロー";
	
	Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//timeline取得中プログレスバー
		final ProgressDialog dialog = new ProgressDialog(this);
		dialog.setMessage("timeline取得中");
		dialog.show();
		new Thread(new Runnable() {
			public void run() {
				// ユーザー名からtimeline取得
				String screenName = getIntent().getStringExtra("screen_name");
				final List<String> list = TwitterRequest.getUserTimeline(screenName);
				handler.post(new Runnable() {
					public void run() {
						setListAdapter(new ArrayAdapter<String>(TimeLineActivity.this, R.layout.timeline_item,list));
						dialog.cancel();
					}
				});
			}
		}).start();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//フォローボタン
		menu.add(0, FOLLOW, 0, FOLLOW_LABEL);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case FOLLOW:
			//指定ユーザーをフォローする
			String screen_name = getIntent().getStringExtra("screen_name");
			TwitterRequest.createFriendship(this, screen_name);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
