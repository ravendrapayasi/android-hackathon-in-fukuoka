package jp.jagfukuoka.sodefuri.service;

import java.util.Date;

import jp.jagfukuoka.sodefuri.provider.RecentContentProvider;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BluetoothFoundReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "Bluetooth‚ªŒ©‚Â‚©‚è‚Ü‚µ‚½", Toast.LENGTH_LONG).show();
        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        Date date = new Date();
        ContentValues values = new ContentValues();
        values.put(RecentContentProvider.MAC_ADDRESS, device.getAddress());
        values.put(RecentContentProvider.TIME, date.getTime());
        context.getContentResolver().insert(RecentContentProvider.CONTENT_URI, values);
	}

}
