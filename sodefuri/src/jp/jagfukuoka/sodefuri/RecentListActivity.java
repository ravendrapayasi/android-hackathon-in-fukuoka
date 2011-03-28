package jp.jagfukuoka.sodefuri;

import java.util.List;

import jp.jagfukuoka.sodefuri.login.NewAccountActivity;
import jp.jagfukuoka.sodefuri.preference.TwitterPreferences;
import jp.jagfukuoka.sodefuri.provider.ProviderManager;
import jp.jagfukuoka.sodefuri.provider.RecentContentProvider;
import jp.jagfukuoka.sodefuri.server.MatchingServer;
import jp.jagfukuoka.sodefuri.server.twitter.TwitterRequest;
import jp.jagfukuoka.sodefuri.service.BluetoothFoundReceiver;
import jp.jagfukuoka.sodefuri.service.RecentReceiver;
import jp.jagfukuoka.sodefuri.service.RecentService;
import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * すれ違った順番にスクリーンネームが表示されるActivity
 * 
 * @author shikajiro
 * 
 */
public class RecentListActivity extends ListActivity {
	
	/*debug data*/
	//debug flag
	private boolean debug = true;
	//token clear
	private static final int DEBUG_TOKEN_CLEAR_ID = -1;
	//recent list data clear
	private static final int DEBUG_RECENT_CLEAR_ID = -2;
	//add recent data
	private static final int DEBUG_RECENT_ADD_ID = -3;

	//menu ID
	private static final int UPDATE_ID = 1;
	
	//bluetooth ID
	private static final int REQUEST_ENABLE_BT = 1;
	
	private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

	//thread handler
	private Handler handler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(!isBluetoothSupport()){
			// Bluetoothはサポートされてません
			Toast.makeText(this, "Bluetoothがサポートされていないため、このアプリは利用できません。", Toast.LENGTH_LONG).show();
			return;
		}
		
		//twitterTokenが無ければ登録画面へ遷移する
		if(!TwitterPreferences.isAccessToken(this)){
			startActivity(new Intent(this,NewAccountActivity.class));
			return;
		}

		//test daba insert
		if(debug){
	        ContentValues values = new ContentValues();
			values.put(RecentContentProvider.MAC_ADDRESS,"00:00:00:00:00:00");
			values.put(RecentContentProvider.MAC_ADDRESS,"E8:E5:D6:4C:52:3A");// _simo
			values.put(RecentContentProvider.MAC_ADDRESS,"F8:DB:7F:02:2E:EE");// shikajiro
	        getContentResolver().insert(RecentContentProvider.CONTENT_URI, values);
		}
		
		//bluetoothの情報をサーバーに登録する。
		//bluetoothが使えない場合は終了する。
		registerBluetooth();

		// bluetooth自動検索serviceのタイマー
		startService(new Intent(this, RecentService.class));
		// bluetooth検索処理
		registerReceiver(new RecentReceiver(), new IntentFilter(RecentService.SEARCH));
		// bluetoothデバイスが見つかった時の登録処理
		registerReceiver(new BluetoothFoundReceiver(), new IntentFilter(BluetoothDevice.ACTION_FOUND));
		
		// データ追加後の画面描画処理とnotification
		ContentObserver contentObserver = new ContentObserver(handler) {
			@Override
			public void onChange(boolean selfChange) {
				// ListViewの更新
				RecentAdapter recentAdapter = new RecentAdapter(
						getApplicationContext(), R.layout.recent_list,
						getRecentBeans());
				setListAdapter(recentAdapter);
				
				// notificationによるユーザーへの通知
				NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
				Notification notification = new Notification(
						android.R.drawable.btn_default, "すれ違いました",
						System.currentTimeMillis());
				Intent intent = new Intent(getApplicationContext(),
						RecentListActivity.class);
				PendingIntent contentIntent = PendingIntent.getActivity(
						getApplicationContext(), 0, intent, 0);
				notification.setLatestEventInfo(getApplicationContext(),
						"sodefuri", "多少の縁がありました。", contentIntent);
				notificationManager.notify(R.string.app_name, notification);
				super.onChange(selfChange);
			}
		};
		getContentResolver().registerContentObserver(
				RecentContentProvider.CONTENT_URI, true, contentObserver);

		//今まですれ違ったデータをリストに表示する。
		final ProgressDialog dialog = new ProgressDialog(this);
		dialog.setMessage("すれ違い情報取得中");
		dialog.show();
		new Thread(new Runnable() {
			public void run() {
				final List<RecentBean> recents = getRecentBeans();
				handler.post(new Runnable() {
					public void run() {
						setListAdapter(new RecentAdapter(
								RecentListActivity.this,
								R.layout.recent_list, recents));
						dialog.cancel();
					}
				});
			}
		}).start();

		// 選択したユーザーのタイムラインを表示する。
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView textView = (TextView) view.findViewById(R.id.toptext);
				CharSequence screen_name = textView.getText();
				Intent intent = new Intent(RecentListActivity.this,
						TimeLineActivity.class);
				intent.putExtra("screen_name", screen_name);
				startActivity(intent);
			}
		});

	}

	private boolean isBluetoothSupport() {
		if (mBluetoothAdapter == null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (debug) {
			menu.add(0, DEBUG_TOKEN_CLEAR_ID, 0, "token clear");
			menu.add(0, DEBUG_RECENT_CLEAR_ID, 0, "recent clear");
			menu.add(0, DEBUG_RECENT_ADD_ID, 0, "add testdata");
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		//debug
		case DEBUG_TOKEN_CLEAR_ID:
			TwitterPreferences.clearAccessToken(this);
			break;
		case DEBUG_RECENT_CLEAR_ID:
			getContentResolver().delete(null, null, null);
			break;
		case DEBUG_RECENT_ADD_ID:
			ContentValues values = new ContentValues();
			values.put(RecentContentProvider.MAC_ADDRESS, "00:11:22:33:44:55");
			getContentResolver().insert(RecentContentProvider.CONTENT_URI,
					values);
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * serverからmac_addressをもとにscreen_nameを取得する
	 * 
	 * @return
	 */
	private List<RecentBean> getRecentBeans() {

		// DBに保存されている今まですれ違ったMAC_ADDRESSの一覧を取得
		List<String> columnData = ProviderManager.getColumnData(this);
		
		// MAC_ADDRESSの一覧を元にサーバーからスクリーンネームの一覧を取得する
		List<String> list = MatchingServer.findName(columnData);
		
		//スクリーンネームの一覧をviewに表示するためのbeansを作成する
		List<RecentBean> beans = RecentBean.createBeans(this, list);

		return beans;
	}

	/**
	 * Bluetoothのmac_addressを取得する
	 */
	private void registerBluetooth() {
		if (mBluetoothAdapter.isEnabled()) {
			// -----[利用可能なので次の処理]
			String macAddress = mBluetoothAdapter.getAddress();
			String screenName = TwitterRequest.getMyScreenName(this);
			MatchingServer.register(screenName, macAddress);
		} else {
			// -----[利用不可なので許可アラート表示]
			Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBTIntent, REQUEST_ENABLE_BT);
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
			String macAddress = mBluetoothAdapter.getAddress();
			String screenName = TwitterRequest.getMyScreenName(this);
			MatchingServer.register(screenName, macAddress);
		} else if (resultCode == RESULT_CANCELED) {
			// Bluetoothは利用不可です
			Toast.makeText(this, "Bluetoothを起動できないため、このアプリは利用できません。", Toast.LENGTH_LONG).show();
			finish();
		}
	}

}
