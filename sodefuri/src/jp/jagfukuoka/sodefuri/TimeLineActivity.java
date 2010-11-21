package jp.jagfukuoka.sodefuri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

public class TimeLineActivity extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// timeline取得処理
		String screenName = getIntent().getStringExtra("screen_name");
		List<String> list = getTimeLine(screenName);

		setListAdapter(new ArrayAdapter<String>(this, R.layout.timeline_item, list));
	}

	private List<String> getTimeLine(String screenName) {
		List<String> result = new ArrayList<String>();

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet request = new HttpGet("http://api.twitter.com/1/statuses/public_timeline.json");
		try {
			HttpResponse response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				Toast.makeText(this, "publicタイムラインを取得しました", Toast.LENGTH_LONG)
						.show();

				InputStream is = response.getEntity().getContent();
				BufferedReader br = new BufferedReader(
						new InputStreamReader(is));
				String line;
				String responsJson = "";
				while ((line = br.readLine()) != null) {
					responsJson += line;
				}

				JSONArray jsonArrays = new JSONArray(responsJson);
				for (int i = 0; i < jsonArrays.length(); i++) {
					JSONObject jsonObj = jsonArrays.getJSONObject(i);
					String screen_name = jsonObj.getString("text");
					result.add(screen_name);
				}
			} else {
				Toast.makeText(this,
						"[タイムライン取得error]: " + response.getStatusLine(),
						Toast.LENGTH_LONG).show();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
}
