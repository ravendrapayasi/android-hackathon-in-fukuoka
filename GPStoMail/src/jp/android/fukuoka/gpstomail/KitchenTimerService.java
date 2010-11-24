package jp.android.fukuoka.gpstomail;

import java.util.Timer;
import java.util.TimerTask;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class KitchenTimerService extends Service {
	private boolean DebugMode = true;
	
	class KitchenTimerBinder extends Binder {
		
		KitchenTimerService getService() {
			return KitchenTimerService.this;
		}
		
	}
	
	public static final String ACTION = "GPStoMail Timer Service";
	private Timer timer;
	
	@Override
	public void onCreate() {
		super.onCreate();
		if(DebugMode){
		Toast toast = Toast.makeText(getApplicationContext(), "onCreate()", Toast.LENGTH_SHORT);
		toast.show();
		Log.i(ACTION,"####### service onCreate() process:"+ android.os.Process.myPid() + " task:" + android.os.Process.myTid());
		}
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		if(DebugMode){
		Toast toast = Toast.makeText(getApplicationContext(), "onStart()", Toast.LENGTH_SHORT);
		toast.show();
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(DebugMode){
		Toast toast = Toast.makeText(getApplicationContext(), "onDestroy()", Toast.LENGTH_SHORT);
		toast.show();
		}
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		if(DebugMode){
		Toast toast = Toast.makeText(getApplicationContext(), "onBind()", Toast.LENGTH_SHORT);
		toast.show();
		}
		return new KitchenTimerBinder();
	}
	
	@Override
	public void onRebind(Intent intent) {
		if(DebugMode){
		Toast toast = Toast.makeText(getApplicationContext(), "onRebind()", Toast.LENGTH_SHORT);
		toast.show();
		}
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		if(DebugMode){
		Toast toast = Toast.makeText(getApplicationContext(), "onUnbind()", Toast.LENGTH_SHORT);
		toast.show();
		}
		return true; // 再度クライアントから接続された際に onRebind を呼び出させる場合は true を返す
	}
	
	// クライアントから呼び出されるメソッド
	public void schedule(long delay) {
		if (timer != null) {
			timer.cancel();
		}
		timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			public void run() {
				sendBroadcast(new Intent(ACTION));
			}
		};
		timer.schedule(timerTask, delay);
	}
}