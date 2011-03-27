package jp.jagfukuoka.sodefuri;
import jp.jagfukuoka.sodefuri.login.NewAccountActivity;
import jp.jagfukuoka.sodefuri.preference.TwitterPreferenceManager;
import jp.jagfukuoka.sodefuri.provider.RecentContentProvider;
import jp.jagfukuoka.sodefuri.service.BluetoothFoundReceiver;
import jp.jagfukuoka.sodefuri.service.RecentReceiver;
import jp.jagfukuoka.sodefuri.service.RecentService;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class MainActivity extends Activity {
	private TwitterPreferenceManager tpm = new TwitterPreferenceManager(this);  
	boolean debug = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//test daba insert
		if(debug){
	        ContentValues values = new ContentValues();
			values.put(RecentContentProvider.MAC_ADDRESS,"00:00:00:00:00:00");
			values.put(RecentContentProvider.MAC_ADDRESS,"E8:E5:D6:4C:52:3A");// _simo
			values.put(RecentContentProvider.MAC_ADDRESS,"F8:DB:7F:02:2E:EE");// shikajiro
	        getContentResolver().insert(RecentContentProvider.CONTENT_URI, values);
		}
		
		//twitterTokenが無ければ登録画面へ遷移する
		if(!tpm.isAccessToken()){
			startActivity(new Intent(this,NewAccountActivity.class));
			return;
		}
		
		// bluetooth検索serviceの起動
		startService(new Intent(this, RecentService.class));
		// bluetooth検索処理
		registerReceiver(new RecentReceiver(), new IntentFilter(RecentService.SEARCH));
		// bluetoothデバイスが見つかった時
		registerReceiver(new BluetoothFoundReceiver(), new IntentFilter(BluetoothDevice.ACTION_FOUND));
		
		startActivity(new Intent(this, RecentListViewActivity.class));
	}
}
