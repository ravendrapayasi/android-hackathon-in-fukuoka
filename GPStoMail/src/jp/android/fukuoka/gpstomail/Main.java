package jp.android.fukuoka.gpstomail;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
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
//	private AlarmManager am;
//	private PendingIntent sender;
	
	private String TAG = "GPS2MAIL";
	private boolean DebugMode = true;
	private CheckBox mCheckBox;

	
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
				intentgps.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intentgps);

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
		timePicker.setCurrentHour(0);
		timePicker.setCurrentMinute(1);
		
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
	}

	public void onClick(View view) {
		switch(view.getId()){
		case R.id.Button_END:
			//アプリ終了
			finish();
			break;
			
		case R.id.Button_SET:
			//AlarmManagerで実装？
//			Intent intent = new Intent(this, KitchenTimerReceiver.class);
//			intent.setAction(ACTION);        
//			    
//			sender = PendingIntent.getBroadcast(this, 0, intent, 0);
//			am = (AlarmManager)(this.getSystemService(ALARM_SERVICE));
//			
//			long hour = timePicker.getCurrentHour();
//			long min = timePicker.getCurrentMinute();
//			
//			long interval = ((hour * 60 + min) * 60 * 1000);
//			long firstTime = SystemClock.elapsedRealtime();
//			if (!mCheckBox.isChecked()){
//				firstTime =+ interval;
//			}
//			am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,firstTime , interval, sender);
			
			//タイマー設定処理
			long hour = timePicker.getCurrentHour();
			long min = timePicker.getCurrentMinute();
			
			//Timer set
			kitchenTimerService.schedule((hour * 60 + min) * 60 * 1000);
			
			//SendMail Immediateがtrueなら送信
			if (mCheckBox.isChecked()){
				//位置情報取得処理
				intentgps = new Intent(this ,LocationGet.class);
				intentgps.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intentgps);
			}

			//Activity を非表示へセット
//			moveTaskToBack(true);
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
//		am.cancel(sender);
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
//		am.cancel(sender);
	}
	
}