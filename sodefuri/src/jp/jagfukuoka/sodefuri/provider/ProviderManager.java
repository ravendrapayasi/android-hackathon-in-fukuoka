package jp.jagfukuoka.sodefuri.provider;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;

public class ProviderManager {

	/**
	 * DBからmac_addressのデータを抽出する
	 * 
	 * @param cur
	 * @return
	 */
	public static List<String> getColumnData(Activity activity){
		List<String> list = new ArrayList<String>();
		String[] projection = new String[] { RecentContentProvider.MAC_ADDRESS };
		Cursor cur = activity.managedQuery(RecentContentProvider.CONTENT_URI,
				projection, null, null, null);
		if (cur.moveToFirst()) {
			int int_mac_address = cur
					.getColumnIndex(RecentContentProvider.MAC_ADDRESS);
			do {
				// Get the field values
				String mac_address = cur.getString(int_mac_address);
				// map.put(MAC_ADDRESS, mac_address);
				// map.put(SCREEN_NAME, screen_name);
				list.add(mac_address);
			} while (cur.moveToNext());
		}
		return list;
	}
}
