package jp.jagfukuoka.sodefuri;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import jp.jagfukuoka.sodefuri.provider.RecentContentProvider;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RecentListViewActivity extends ListActivity {
	boolean debug = true;
	public static final String SCREEN_NAME = "SCREEN_NAME";
	private static final String REQUEST_URL = "http://sodefuri.appspot.com/find_name";

	String[] projection = new String[] { RecentContentProvider.MAC_ADDRESS, };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// debug mac_addressの一覧表示
		// DB 取得処理
//		Cursor managedCursor = managedQuery(RecentContentProvider.CONTENT_URI, projection, null, null, null);
//		List<String> list = this.getColumnData(managedCursor);
//		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item,list));
		
		//サーバーにscreen_nameとmac_addressを登録する
		List<String> screenNames = getScreenName();
		Log.d("screenNames : ", screenNames.toString());
		
		//Listへの登録
		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, screenNames));
		
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// 選択したscreenameをIntentで渡す。
				TextView textView = (TextView)view;
				CharSequence screen_name = textView.getText();
				
				Intent intent = new Intent(RecentListViewActivity.this,TimeLineActivity.class);
				intent.putExtra("screen_name", screen_name);
				startActivity(intent);
			}

		});
	}

	/**
	 * serverからmac_addressをもとにscreen_nameを取得する
	 * 
	 * @return
	 */
	private List<String> getScreenName() {

		// json作成
		// test data get
		String json = this.getMacAddressJson();

		// -----[POST送信するListを作成]
		List<String> result = requestJson(json);

		return result;
	}

	private List<String> requestJson(String json) {
		List<String> result = new ArrayList<String>();
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("json", json));

		try {
			// -----[POST送信]
			// -----[クライアント設定]
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(REQUEST_URL);
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
			HttpResponse response = httpclient.execute(httpPost);
			
			// -----[サーバーからの応答を取得]
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				Toast.makeText(RecentListViewActivity.this, "スクリーンネームを取得しました",
						Toast.LENGTH_LONG).show();
				
				InputStream is = response.getEntity().getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String line;
				String responsJson = "";
		        while((line = br.readLine()) != null){
		        	responsJson += line;
		        }
		        
		        JSONArray jsonArrays = new JSONArray(responsJson);
				for (int i = 0; i < jsonArrays.length(); i++) {
				    JSONObject jsonObj = jsonArrays.getJSONObject(i);
				    String screen_name = jsonObj.getString("screen_name");
				    result.add(screen_name);
				}

			} else {
				Toast.makeText(RecentListViewActivity.this,
						"[スクリーンネーム取得error]: " + response.getStatusLine(),
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

	private String getMacAddressJson() {
		Cursor managedCursor = managedQuery(RecentContentProvider.CONTENT_URI,
				projection, null, null, null);
		JSONArray array = new JSONArray();
		try {
			List<String> list = getColumnData(managedCursor);
			for (String mac_address : list) {
				JSONObject obj = new JSONObject();
				obj.put(RecentContentProvider.MAC_ADDRESS, mac_address);
				array.put(obj);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String json = array.toString();
		return json;
	}

	/**
	 * DBからmac_addressのデータを抽出する
	 * 
	 * @param cur
	 * @return
	 */
	private List<String> getColumnData(Cursor cur) {
		List<String> list = new ArrayList<String>();
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
