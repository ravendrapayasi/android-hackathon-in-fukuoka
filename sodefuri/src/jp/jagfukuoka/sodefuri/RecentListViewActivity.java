package jp.jagfukuoka.sodefuri;

import java.io.BufferedReader;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * �����������ԂɃX�N���[���l�[�����\�������Activity
 * @author shikajiro
 *
 */
public class RecentListViewActivity extends ListActivity {
	private static final String FIND_SCREEN_NAME_URL = "http://sodefuri.appspot.com/find_name";

	boolean debug = true;
	public static final String SCREEN_NAME = "SCREEN_NAME";

	String[] projection = new String[] { RecentContentProvider.MAC_ADDRESS, };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//�T�[�o�[��screen_name��mac_address��o�^����
		List<String> screenNames = this.getScreenName();
		
		//List�ւ̓o�^
		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, screenNames));
		
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// �I������screename��Intent�œn���B
				TextView textView = (TextView)view;
				CharSequence screen_name = textView.getText();
				
				Intent intent = new Intent(RecentListViewActivity.this,TimeLineActivity.class);
				intent.putExtra("screen_name", screen_name);
				startActivity(intent);
			}

		});
	}

	/**
	 * server����mac_address�����Ƃ�screen_name���擾����
	 * 
	 * @return
	 */
	private List<String> getScreenName() {

		// json�쐬
		String json = this.getMacAddressJson();

		// json�ŃX�N���[���l�[����₢���킹��
		List<String> list = this.findScreenNames(json);
		return list;
	}

	/**
	 * �X�N���[���l�[���擾����
	 * 
	 * @param json
	 * @param handler
	 * @return
	 */
	public List<String> findScreenNames(String json) {
		List<String> result = new ArrayList<String>();
		try {
			// -----[POST���M]
			// -----[�N���C�A���g�ݒ�]
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
			nameValuePair.add(new BasicNameValuePair("json", json));
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(FIND_SCREEN_NAME_URL);
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
			HttpResponse response = httpclient.execute(httpPost);

			// -----[�T�[�o�[����̉������擾]
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				Toast.makeText(RecentListViewActivity.this, "�X�N���[���l�[�����擾���܂���", Toast.LENGTH_LONG).show();

				InputStream is = response.getEntity().getContent();
				BufferedReader br = new BufferedReader(
						new InputStreamReader(is));
				String responsJson = "";
				String line;
				while ((line = br.readLine()) != null) {
					responsJson += line;
				}

				JSONArray jsonArrays = new JSONArray(responsJson);
				for (int i = 0; i < jsonArrays.length(); i++) {
					JSONObject jsonObj = jsonArrays.getJSONObject(i);
					String screen_name = jsonObj.getString("screen_name");
					result.add(screen_name);
				}

		} else {
			Toast.makeText(RecentListViewActivity.this, "[�X�N���[���l�[���擾error]: " + response.getStatusLine(),
					Toast.LENGTH_LONG).show();
		}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * json�^��mac_address�ꗗ���擾����
	 * @return
	 */
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
			e.printStackTrace();
		}
		return  array.toString();
	}

	/**
	 * DB����mac_address�̃f�[�^�𒊏o����
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