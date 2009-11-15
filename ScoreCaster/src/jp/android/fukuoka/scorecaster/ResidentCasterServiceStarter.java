package jp.android.fukuoka.scorecaster;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 起動時にサービスを起動させる。ブロードキャストレシーバ。
 */
public class ResidentCasterServiceStarter extends BroadcastReceiver {
	public static final String BROADCAST_START_ACTION ="BROADCAST_START_ACTION";
	public static final String BROADCAST_STOP_ACTION ="BROADCAST_STOP_ACTION";

	@Override
	public void onReceive(Context context, Intent intent) {
		// 起動時にキック
		if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
			Log.d("ResidentCasterServiceStarter","Start caster service");
			Intent serviceIntent = new Intent(context,ResidentCasterService.class);
			serviceIntent.setAction(ResidentCasterService.START_ACTION);
			context.startService(serviceIntent);
		}
	}

}
