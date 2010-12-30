package jp.jagfukuoka.sodefuri;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import jp.jagfukuoka.sodefuri.preference.TwitterPreferenceManager;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.http.RequestToken;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * 登録画面Activity
 * 
 * @author _simo
 * @author shikajiro
 * 
 */
public class NewAccountActivity extends Activity implements OnClickListener {
	private static final String REGISTER_URL = "http://sodefuri.appspot.com/register";

	private static final int REQUEST_ENABLE_BT = 1;
	private static final int REQUEST_STATE_CHANGE_BT = 2;
	private BluetoothAdapter mBluetoothAdapter = null;
	private TwitterPreferenceManager tpm = new TwitterPreferenceManager(this);  

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
		if(tpm.isRequestToken()){
			startActivity(new Intent(this,PinActivity.class));
		}else if(tpm.isAccessToken()){
			startActivity(new Intent(this,RecentListViewActivity.class));
		}
	}
	
	/**
	 * マウスクリック処理
	 */
	@Override
	public void onClick(View v) {
		ConfigurationBuilder builder = new ConfigurationBuilder();
		Configuration conf = builder.setDebugEnabled(true)
		.setOAuthConsumerKey(TwitterPreferenceManager.CONSUMER_KEY)
		.setOAuthConsumerSecret(TwitterPreferenceManager.CONSUMER_SERCRET)
		.build();
		Twitter twitter = new TwitterFactory(conf).getInstance();
		try {
			RequestToken requestToken = twitter.getOAuthRequestToken();
			String authorizationURL = requestToken.getAuthorizationURL();
			tpm.storeRequestToken(requestToken.getToken(), requestToken.getTokenSecret());
			// twitter認証画面へ
			Uri uri = Uri.parse(authorizationURL);
			startActivity(new Intent(Intent.ACTION_VIEW,uri));
			
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Bluetoothのmac_addressを取得する
	 */
	private void checkBluetooth() {
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		if (mBluetoothAdapter == null) {
			// Bluetoothはサポートされてません
			Toast.makeText(NewAccountActivity.this, "Bluetoothはサポートされてません",
					Toast.LENGTH_LONG).show();
			return;
		}

		if (mBluetoothAdapter.isEnabled()) {
			// -----[利用可能なので次の処理]
			String mac_address = mBluetoothAdapter.getAddress();
//			this.registerScreenName(screen_name, mac_address);
		} else {
			// -----[利用不可なので許可アラート表示]
			Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBTIntent, REQUEST_ENABLE_BT);
			// Intent stateChangedBTIntent = new
			// Intent(BluetoothAdapter.ACTION_STATE_CHANGED);
			// startActivityForResult(stateChangedBTIntent,REQUEST_STATE_CHANGE_BT);
		}
	}

	/**
	 * Bluetoothが起動されておらず、ユーザーにより起動が許可された場合に呼ばれる。
	 * Bluetoothのmacaddressを取得し、サーバーに送信する
	 * 
	 * @author _simo
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// if (requestCode != REQUEST_ENABLE_BT) {
		// return;
		// }

		if (resultCode == RESULT_OK) {
			// Bluetoothが利用可能になりました
			String mac_address = mBluetoothAdapter.getAddress();
//			String screen_name = ((EditText) findViewById(R.id.EditText01))
//					.getText().toString();
//			this.registerScreenName(screen_name, mac_address);
		} else if (resultCode == RESULT_CANCELED) {
			// Bluetoothは利用不可です
		}
	}

	/**
	 * twitterのスクリーンネームとbluetoothのmacAddress登録処理
	 * 
	 * @param mac_address
	 */
	private void registerScreenName(String screen_name, String mac_address) {

		// -----[クライアント設定]
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(REGISTER_URL);

		// -----[JSONの作成]
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("screen_name", screen_name);
			jsonObject.put("mac_address", mac_address);

			String json = jsonObject.toString();

			// -----[POST送信するListを作成]
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
			nameValuePair.add(new BasicNameValuePair("json", json));

			// -----[POST送信]
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));
			HttpResponse response = httpclient.execute(httppost);
			// -----[サーバーからの応答を取得]
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				Toast.makeText(NewAccountActivity.this, "スクリーンネームとブルートゥース機器を登録しました。",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(
						NewAccountActivity.this,
						"[error]:スクリーンネームとブルートゥース機器を登録できませんでした。"
								+ response.getStatusLine(), Toast.LENGTH_LONG)
						.show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}