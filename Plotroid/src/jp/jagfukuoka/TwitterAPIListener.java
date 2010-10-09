package jp.jagfukuoka;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.util.Log;
import android.view.View;

public class TwitterAPIListener implements View.OnClickListener {

	private final String TAG = "TwitterAPIListener";
	
	public void onClick(View v) {
		HttpClient httpClient = new DefaultHttpClient();
		
		HttpGet request = new HttpGet("http://api.twitter.com/1/statuses/public_timeline.json");
		
		HttpResponse httpResponse = null;
		try{
			httpResponse = httpClient.execute(request);
			Log.d(TAG, "http ok");
		}catch (Exception e) {
			e.printStackTrace();
			Log.d(TAG, "http ng");
		}

        String json = null;
		try{
			InputStream is = httpResponse.getEntity().getContent();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;
	        while((line = br.readLine()) != null){
	            json = line;
	        }
			Log.d(TAG, "stream ok\n"+json);
		}catch (Exception e) {
			e.printStackTrace();
			Log.d(TAG, "stream ng");
		}
		
		String description = null;
		try {
			JSONArray jsons = new JSONArray(json);
			for (int i = 0; i < jsons.length(); i++) {
			    JSONObject jsonObj = jsons.getJSONObject(i);
			    description = jsonObj.getString("description");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
			
	}

}
