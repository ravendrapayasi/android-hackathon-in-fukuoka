package jp.jagfukuoka.sodefuri.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import jp.jagfukuoka.sodefuri.server.converter.JSONConverter;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

/**
 * MAC_ADDRESSとtwitterIDを関連付けているサーバーに問い合わせるクラス
 * 
 * @author shikajiro
 * 
 */
public class MatchingServer {
	//マッチングサーバーのURL
	private static final String FIND_SCREEN_NAME_URL = "http://sodefuri.appspot.com/find_name";

	// logging
	private static final String MATCHING_TAG = "tiwitter_access";

	/**
	 * スクリーンネーム取得処理
	 * 
	 * @param macaddreses
	 * @return
	 */
	public static List<String> findName(List<String> macaddreses) {
		String json = JSONConverter.convertMacAddressJson(macaddreses);
		List<String> result = new ArrayList<String>();

		try {
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
			nameValuePair.add(new BasicNameValuePair("json", json));
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(FIND_SCREEN_NAME_URL);
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
			HttpResponse response = httpclient.execute(httpPost);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				Log.i(MATCHING_TAG, "スクリーンネームを取得しました");

				InputStream is = response.getEntity().getContent();
				result = JSONConverter.conertScreenNameJsons(is);

			} else {
				Log.i(MATCHING_TAG, "スクリーンネームの取得に失敗しました");
			}
		} catch (UnsupportedEncodingException e) {
			Log.i(MATCHING_TAG, "レスポンス取得に失敗しました");
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			Log.i(MATCHING_TAG, "レスポンス取得に失敗しました");
			e.printStackTrace();
		} catch (IOException e) {
			Log.i(MATCHING_TAG, "レスポンス取得に失敗しました");
			e.printStackTrace();
		}

		return result;
	}
}
