package jp.jagfukuoka.sodefuri;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import jp.jagfukuoka.sodefuri.provider.RecentContentProvider;
import jp.jagfukuoka.sodefuri.service.RecentReceiver;
import jp.jagfukuoka.sodefuri.service.RecentService;

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

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 登録画面Activity
 * 
 * @author _simo
 * @author shikajiro
 * 
 */
public class MainActivity extends Activity {
	private static final String REGISTER_URL = "http://sodefuri.appspot.com/register";

	private static final int REQUEST_ENABLE_BT = 1;
	private static final int REQUEST_STATE_CHANGE_BT = 2;
	private BluetoothAdapter mBluetoothAdapter = null;
	private static final boolean isDebug = true;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// -----[登録ボタンの設定]
		Button okButton = (Button) findViewById(R.id.OKButton);
		okButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.OKButton:
					// TODO thread処理化
					ProgressDialog.show(MainActivity.this, null, "登録中...", true);
					checkBluetooth();
					startActivity(new Intent(MainActivity.this,	RecentListViewActivity.class));
					break;
				}
			}
		});
		// debug用Listボタン
		// RecentListViewに遷移する
		Button listButton = (Button) findViewById(R.id.ListButton);
		listButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this,	RecentListViewActivity.class));
				// debug. test data insert
				if (isDebug) {
					ContentValues values = new ContentValues();
					values.put(RecentContentProvider.MAC_ADDRESS,"00:00:00:00:00:00");
					values.put(RecentContentProvider.MAC_ADDRESS,"E8:E5:D6:4C:52:3A");// _simo
					values.put(RecentContentProvider.MAC_ADDRESS,"F8:DB:7F:02:2E:EE");// shikajiro
					getContentResolver().insert(RecentContentProvider.CONTENT_URI, values);
				}
			}
		});
		Button BtFoundButton = (Button) findViewById(R.id.BtFoundButton);
		BtFoundButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, BluetoothFoundActivity.class));
			}
		});
		// serviceの起動
		startService(new Intent(this,RecentService.class));
	    registerReceiver(new RecentReceiver(), new IntentFilter(RecentService.MEET));
	}

	/**
	 * Bluetoothのmac_addressを取得する
	 */
	private void checkBluetooth() {
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		if (mBluetoothAdapter == null) {
			// Bluetoothはサポートされてません
			Toast.makeText(MainActivity.this, "Bluetoothはサポートされてません",	Toast.LENGTH_LONG).show();
			return;
		}

		if (mBluetoothAdapter.isEnabled()) {
			// -----[利用可能なので次の処理]
			String mac_address = mBluetoothAdapter.getAddress();
			String screen_name = ((EditText) findViewById(R.id.EditText01))
					.getText().toString();
			this.registerScreenName(screen_name, mac_address);
		} else {
			// -----[利用不可なので許可アラート表示]
			Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBTIntent, REQUEST_ENABLE_BT);
//			Intent stateChangedBTIntent = new Intent(BluetoothAdapter.ACTION_STATE_CHANGED);
//			startActivityForResult(stateChangedBTIntent,REQUEST_STATE_CHANGE_BT);
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
//		if (requestCode != REQUEST_ENABLE_BT) {
//			return;
//		}

		if (resultCode == RESULT_OK) {
			// Bluetoothが利用可能になりました
			String mac_address = mBluetoothAdapter.getAddress();
			String screen_name = ((EditText) findViewById(R.id.EditText01))
					.getText().toString();
			this.registerScreenName(screen_name, mac_address);
		} else if (resultCode == RESULT_CANCELED) {
			// Bluetoothは利用不可です
		}
	}

	/**
	 * twitterのスクリーンネームとbluetootuのmacAddress登録処理
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
				Toast.makeText(MainActivity.this, "スクリーンネームとブルートゥース機器を登録しました。",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(
						MainActivity.this,
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