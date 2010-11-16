package jp.jagfukuoka.sodefuri;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
public class MainActivity extends Activity{

	private static final String REQUEST_URL = "http://sodefuri.appspot.com/register";

	private static final int REQUEST_ENABLE_BT = 1;
	private static final int REQUEST_STATE_CHANGE_BT = 2;
	private BluetoothAdapter mBluetoothAdapter = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// -----[ボタンの設定]
		Button button1 = (Button) findViewById(R.id.OKButton);
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.OKButton:
					ProgressDialog.show(MainActivity.this, null, "登録中...", true);
					checkBluetooth();
					startActivity(new Intent(MainActivity.this,
							RecentListViewActivity.class));
					break;
				}
			}
		});
		
		Button listButton = (Button) findViewById(R.id.ListButton);
		listButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this,RecentListViewActivity.class));
			}
		});
	}

	/**
	 * Bluetoothのmac_addressを取得する
	 */
	private void checkBluetooth() {
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		if (mBluetoothAdapter == null) {
			// Bluetoothはサポートされてません
			return;
		}

		if (mBluetoothAdapter.isEnabled()) {
			// -----[利用可能なので次の処理]
			String mac_address = mBluetoothAdapter.getAddress();
			sendJson(mac_address);
		} else {
			// -----[利用不可なので許可アラート表示]
			Intent enableBTIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBTIntent, REQUEST_ENABLE_BT);
			Intent stateChangedBTIntent = new Intent(
					BluetoothAdapter.ACTION_STATE_CHANGED);
			startActivityForResult(stateChangedBTIntent,
					REQUEST_STATE_CHANGE_BT);
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
		if (requestCode != REQUEST_ENABLE_BT) {
			return;
		}

		if (resultCode == RESULT_OK) {
			// Bluetoothが利用可能になりました
			String mac_address = mBluetoothAdapter.getAddress();
			sendJson(mac_address);
		} else if (resultCode == RESULT_CANCELED) {
			// Bluetoothは利用不可です
		}
	}

	/**
	 * jsonをPostで送信する
	 * 
	 * @author _simo
	 * @param address
	 */
	private void sendJson(final String address) {

		// -----[クライアント設定]
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(REQUEST_URL);

		// -----[JSONの作成]
		EditText et = (EditText) findViewById(R.id.EditText01);
		String json = "{\"screen_name\":\"" + et.getText()
				+ "\", \"mac_address\":\"" + address + "\"}";

		// -----[POST送信するListを作成]
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
		nameValuePair.add(new BasicNameValuePair("json", json));

		try {
			// -----[POST送信]
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));
			HttpResponse response = httpclient.execute(httppost);
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			response.getEntity().writeTo(byteArrayOutputStream);

			// -----[サーバーからの応答を取得]
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				Toast.makeText(MainActivity.this, "登録しました", Toast.LENGTH_LONG)
						.show();
			} else {
				Toast.makeText(MainActivity.this,
						"[error]: " + response.getStatusLine(),
						Toast.LENGTH_LONG).show();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}