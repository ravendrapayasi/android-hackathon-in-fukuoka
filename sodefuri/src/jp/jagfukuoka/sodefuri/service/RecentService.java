package jp.jagfukuoka.sodefuri.service;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
/**
 * 定期的にbluetoothを検索するサービス
 * @author shikajiro
 *
 */
public class RecentService extends Service {
	protected static final String TAG = "RecentService";
	public static final String SEARCH = "search";
	// タイマーの秒間隔
	private static final int SECOND = 30;

	private Timer timer;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 一定期間ごとにbluetoothを検索する
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				sendBroadcast(new Intent(SEARCH));
			}
		};
		timer.schedule(task, 0, SECOND * 1000);
	}
}
