package jp.jagfukuoka.sodefuri.service;

import java.util.Date;

import jp.jagfukuoka.sodefuri.provider.RecentContentProvider;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BluetoothFoundReceiver extends BroadcastReceiver {

	private static final String BLUETOOTH_TAG = "BLUETOOTH_TAG";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(BLUETOOTH_TAG, "Bluetoothが見つかりました");
        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        Date date = new Date();
        ContentValues values = new ContentValues();
        values.put(RecentContentProvider.MAC_ADDRESS, device.getAddress());
        values.put(RecentContentProvider.TIME, date.getTime());
        context.getContentResolver().insert(RecentContentProvider.CONTENT_URI, values);
	}

}
