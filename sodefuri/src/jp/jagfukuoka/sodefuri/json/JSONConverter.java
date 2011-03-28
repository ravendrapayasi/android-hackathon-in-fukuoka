package jp.jagfukuoka.sodefuri.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import jp.jagfukuoka.sodefuri.provider.RecentContentProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONConverter {
	public static String convertMacAddressJson(List<String> list) {
		JSONArray array = new JSONArray();
		try {
			for (String mac_address : list) {
				JSONObject obj = new JSONObject();
				obj.put(RecentContentProvider.MAC_ADDRESS, mac_address);
				array.put(obj);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return array.toString();
	}

	public static List<String> conertScreenNameJsons(InputStream is) {
		List<String> result = new ArrayList<String>();

		try {
			String responsJson = "";
			String line;

			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				responsJson += line;
			}

			JSONArray jsonArrays = new JSONArray(responsJson);
			for (int i = 0; i < jsonArrays.length(); i++) {
				JSONObject jsonObj = jsonArrays.getJSONObject(i);
				String screen_name = jsonObj.getString("screen_name");
				result.add(screen_name);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}
}
