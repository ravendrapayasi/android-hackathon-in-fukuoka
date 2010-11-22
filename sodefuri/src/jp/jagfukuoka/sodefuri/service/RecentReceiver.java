package jp.jagfukuoka.sodefuri.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * ü•Ó‚Ìbluetooth‚ğŒŸõ‚µ‚ÄADB‚É“o˜^‚·‚éƒŒƒV[ƒo[
 * 
 * @author shikajiro
 * 
 */
public class RecentReceiver extends BroadcastReceiver {
	/**
	 * ü•Ó‚Ìbluetooth‚ğŒŸõ‚·‚é
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "Time over!", Toast.LENGTH_LONG).show();
		// TODO bluetoothŒŸõ
		
		// TODO DB“o˜^
	}

}
