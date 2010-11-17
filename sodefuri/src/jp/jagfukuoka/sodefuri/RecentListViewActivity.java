package jp.jagfukuoka.sodefuri;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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

		// test data insert
		ContentValues values = new ContentValues();
		values.put(RecentContentProvider.MAC_ADDRESS, "ab:cd:ef:ge");
		Uri uri = getContentResolver().insert(
				RecentContentProvider.CONTENT_URI, values);
		// test data get
		Cursor managedCursor = managedQuery(RecentContentProvider.CONTENT_URI,
				projection, null, null, null);
		List<String> list = getColumnData(managedCursor);
		// DB 取得処理
		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, list));

		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// -----[クライアント設定]
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(REQUEST_URL);

				// json作成
				// test data get
				Cursor managedCursor = managedQuery(RecentContentProvider.CONTENT_URI,
						projection, null, null, null);
				JSONArray array = new JSONArray();
				try {
					List<String> list = getColumnData(managedCursor);
					for(String mac_address : list){
						JSONObject obj = new JSONObject();
						obj.put(RecentContentProvider.MAC_ADDRESS, mac_address);
						array.put(obj);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String json = array.toString();

				// -----[POST送信するListを作成]
				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(
						1);
				nameValuePair.add(new BasicNameValuePair("json", json));

				try {
					// -----[POST送信]
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));
					HttpResponse response = httpclient.execute(httppost);
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					response.getEntity().writeTo(byteArrayOutputStream);

					// -----[サーバーからの応答を取得]
					if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						Toast.makeText(RecentListViewActivity.this,
								"スクリーンネームを取得しました", Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(
								RecentListViewActivity.this,
								"[スクリーンネーム取得error]: "
										+ response.getStatusLine(),
								Toast.LENGTH_LONG).show();
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
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
