package jp.android.fukuoka.gpstomail;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class Main extends Activity {
	private Button btn1;
	private Button btn2;
	
	//設定時間で呼び出される処理
	private class KitchenTimerReceiver extends BroadcastReceiver {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			
			//音声再生
			MediaPlayer mp = MediaPlayer.create(Main.this, R.raw.alarm);
	    	Log.i("GPStoMail","Mail Sending");
	    	try {
	    		//タスクをフォアグランドへ戻す処理
	    		onBackPressed();
	    		//位置情報取得処理
	    		Intent intentgps = new Intent(context ,LocationGet.class);
	    		intentgps.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    		
	    		startActivity(intentgps);
				
	    		mp.start();
	    		
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

		final TimePicker timePicker = (TimePicker)findViewById(R.id.TimePicker01);
		timePicker.setIs24HourView(true);
		timePicker.setCurrentHour(0);
		timePicker.setCurrentMinute(1);
		
		btn1 = (Button)findViewById(R.id.Button_SET);
		btn1.setOnClickListener(new View.OnClickListener() {
		
			public void onClick(View view) {
				long hour = timePicker.getCurrentHour();
				long min = timePicker.getCurrentMinute();
				
				//Timer set
				kitchenTimerService.schedule((hour * 60 + min) * 60 * 1000);
				
				//Activity を非表示へセット
				moveTaskToBack(true);
			}
			
		});
		btn2 = (Button)findViewById(R.id.Button_END);
		btn2.setOnClickListener(new View.OnClickListener() {
		
			public void onClick(View view) {
				//アプリ終了
				finish();
			}
			
		});
		
		// サービスを開始
		Intent intent = new Intent(this, KitchenTimerService.class);
		startService(intent);
		IntentFilter filter = new IntentFilter(KitchenTimerService.ACTION);
		registerReceiver(receiver, filter);
		
		// サービスにバインド
		bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

		// いったんアンバインドしてから再度バインド
		unbindService(serviceConnection);
		bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		unbindService(serviceConnection); // バインド解除
		unregisterReceiver(receiver); // 登録解除
		kitchenTimerService.stopSelf(); // サービスは必要ないので終了させる。
	}
}