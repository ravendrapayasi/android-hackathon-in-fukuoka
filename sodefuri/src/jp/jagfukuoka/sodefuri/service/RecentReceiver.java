package jp.jagfukuoka.sodefuri.service;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 周辺のbluetoothを検索して、DBに登録するレシーバー
 * 
 * @author shikajiro
 * 
 */
public class RecentReceiver extends BroadcastReceiver {
	private static final String BLUETOOTH_TAG = "BLUETOOTH_TAG";

	/**
	 * 周辺のbluetoothを検索する
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(BLUETOOTH_TAG, "Bluetooth検索中");
		
		// bluetooth検索
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		bluetoothAdapter.startDiscovery();
	}

}
