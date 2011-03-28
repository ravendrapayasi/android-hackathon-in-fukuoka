package jp.jagfukuoka.sodefuri.login;

import jp.jagfukuoka.sodefuri.R;
import jp.jagfukuoka.sodefuri.RecentListActivity;
import jp.jagfukuoka.sodefuri.preference.TwitterPreferences;
import jp.jagfukuoka.sodefuri.server.twitter.TwitterRequest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 登録画面Activity
 * 
 * @author _simo
 * @author shikajiro
 * 
 */
public class NewAccountActivity extends Activity implements OnClickListener {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_name);

		Button okButton = (Button) findViewById(R.id.OKButton);
		okButton.setOnClickListener(this);

	}
	/**
	 * 認証途中なら暗証番号入力画面に。認証完了後はすれ違いリスト画面に。
	 */
	@Override
	protected void onStart() {
		super.onStart();
		if(TwitterPreferences.isRequestToken(this)){
			startActivity(new Intent(this,PinActivity.class));
		}else if(TwitterPreferences.isAccessToken(this)){
			startActivity(new Intent(this,RecentListActivity.class));
		}
	}
	
	/**
	 * マウスクリック処理
	 */
	public void onClick(View v) {

		// twitter認証画面へ
		String authorizationURL = TwitterRequest.getAuthUrl(this);
		Uri uri = Uri.parse(authorizationURL);
		startActivity(new Intent(Intent.ACTION_VIEW,uri));
	}


}