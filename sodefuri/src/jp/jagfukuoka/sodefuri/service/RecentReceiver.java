package jp.jagfukuoka.sodefuri.service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

/**
 * 周辺のbluetoothを検索して、DBに登録するレシーバー
 * 
 * @author shikajiro
 * 
 */
public class RecentReceiver extends BroadcastReceiver {
	/**
	 * 周辺のbluetoothを検索する
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "Bluetooth検索中", Toast.LENGTH_LONG).show();
		
		// TODO bluetooth検索
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		bluetoothAdapter.startDiscovery();
	}

}
