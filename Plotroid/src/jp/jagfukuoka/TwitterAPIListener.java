package jp.jagfukuoka;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.jagfukuoka.provider.DataContentProvider;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.googlecode.chartdroid.core.IntentConstants;

import android.R.integer;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;
import android.view.View;

public class TwitterAPIListener implements View.OnClickListener {

	private final String TAG = "TwitterAPIListener";
	
	private Map<Integer,List<String>> map = new HashMap<Integer, List<String>>();
	
	public void onClick(View v) {
		
		for(int i=1; i<=28; i++){
			map.put(i*5, new ArrayList<String>());
		}
		
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
			    
			    int res = (description.length()+1)/5;
			    List<String> list = map.get(res*5);
			    list.add(description);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		
		ContentValues values = new ContentValues();
		for(int i=1; i<=28; i++){
			List<String> list = map.get(i*5);
			values.put(""+i*5, list.size());
		}
		ContentResolver cr = v.getContext().getContentResolver();
		cr.insert(DataContentProvider.PROVIDER_URI, values);
	
		// 
    	Intent i = new Intent(Intent.ACTION_VIEW, DataContentProvider.PROVIDER_URI);
    	i.putExtra(Intent.EXTRA_TITLE, TemperatureData.DEMO_CHART_TITLE);
    	i.putExtra(IntentConstants.Meta.Axes.EXTRA_FORMAT_STRING_Y, "%.1f°C");
    	v.getContext().startActivity(i);
	}
	

}
