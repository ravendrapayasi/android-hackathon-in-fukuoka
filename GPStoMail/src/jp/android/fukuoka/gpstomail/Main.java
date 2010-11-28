package jp.android.fukuoka.gpstomail;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;

public class Main extends Activity implements OnClickListener {

	private Button btn1;
	private Button btn2;
	private Button btn3;
	private TimePicker timePicker;
	private Intent intentgps;
	private Intent intent_Service;
	private IntentFilter filter_Service;
	
	private String TAG = "GPS2MAIL";
	private boolean DebugMode = true;
	private CheckBox mCheckBox;
	private LocationManager lm;

	//設定時間で呼び出される処理
	private class KitchenTimerReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			try {
				if(DebugMode){
					Log.i(TAG, "Timer Restar");
				}
				
				//位置情報取得処理
				intentgps = new Intent(context ,LocationGet.class);
				startService(intentgps);
			} catch (Exception e) {
				// 例外は発生しない
			}
		}
	}
	
	private KitchenTimerService kitchenTimerService;
	private final KitchenTimerReceiver receiver = new KitchenTimerReceiver();
	
	private ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
			kitchenTimerService = ((KitchenTimerService.KitchenTimerBinder)service).getService();
		}

		@Override
		public void onServiceDisconnected(ComponentName className) {
			kitchenTimerService = null;
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
		timePicker = (TimePicker)findViewById(R.id.TimePicker01);
		timePicker.setIs24HourView(true);
		timePicker.setCurrentHour(1);
		timePicker.setCurrentMinute(0);
		
		btn1 = (Button)findViewById(R.id.Button_SET);
		btn1.setOnClickListener(this);
		btn2 = (Button)findViewById(R.id.Button_END);
		btn2.setOnClickListener(this);
		btn3 =(Button)findViewById(R.id.Button_PREF);
		btn3.setOnClickListener(this);
		mCheckBox=(CheckBox) findViewById(R.id.CheckBox01);
		
		// サービスを開始
		intent_Service = new Intent(this, KitchenTimerService.class);
		startService(intent_Service);
		//レシーバーの登録
		filter_Service = new IntentFilter(KitchenTimerService.ACTION);
		registerReceiver(receiver, filter_Service);
		
		// サービスにバインド
		bindService(intent_Service, serviceConnection, Context.BIND_AUTO_CREATE);

		// いったんアンバインドしてから再度バインド
		unbindService(serviceConnection);
		bindService(intent_Service, serviceConnection, Context.BIND_AUTO_CREATE);
		
		// LocationManagerでGPSの値を取得するための設定
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		//GPSのOnをチェック
		chkGpsService();
	}

	public void onClick(View view) {
		switch(view.getId()){
		case R.id.Button_END:
			//アプリ終了
			finish();
			break;
			
		case R.id.Button_SET:
			//タイマー設定処理
			long hour = timePicker.getCurrentHour();
			long min = timePicker.getCurrentMinute();
			
			//Timer set
			kitchenTimerService.schedule((hour * 60 + min) * 60 * 1000,mCheckBox.isChecked());

			//Activity を非表示へセット
			moveTaskToBack(true);
			break;
			
		case R.id.Button_PREF:
			// 設定画面の表示
        	Intent i = new Intent(view.getContext(),setting.class);
        	startActivity(i);
			break;
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		unbindService(serviceConnection); // バインド解除
		unregisterReceiver(receiver); // 登録解除
		kitchenTimerService.stopSelf(); // サービスは必要ないので終了させる。
	}
	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	// GPSが有効かCheck
	// 有効になっていなければ、設面の表示確認ダイアログ
	private void chkGpsService() {
		

		//GPSセンサーが利用可能か？
		if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
			alertDialogBuilder.setMessage(R.string.Ch_GPS_Message);
			alertDialogBuilder.setCancelable(false);

			//GPS設定画面起動用ボタンとイベントの定義
			alertDialogBuilder.setPositiveButton(R.string.Ch_GPS_Positive_btn,
					new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int id){
					Intent callGPSSettingIntent = new Intent(
							android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					//					callGPSSettingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(callGPSSettingIntent);
				}
			});
			//キャンセルボタン処理
			alertDialogBuilder.setNegativeButton(R.string.Ch_GPS_Negative_btn,
					new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int id){
					dialog.cancel();
				}
			});
			AlertDialog alert = alertDialogBuilder.create();
			// 設定画面へ移動するかの問い合わせダイアログを表示
			alert.show();
		}
	}
}