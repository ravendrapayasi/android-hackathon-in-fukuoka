package jp.jagfukuoka.sodefuri.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 * MAC_ADDRESSとtwitterIDを関連付けているサーバーに問い合わせるクラス
 * @author shikajiro
 *
 */
public class MatchingServer {
	private static final String FIND_SCREEN_NAME_URL = "http://sodefuri.appspot.com/find_name";
	
	public static HttpResponse findName(String json){
		HttpResponse response = null;
		try {
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
			nameValuePair.add(new BasicNameValuePair("json", json));
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(FIND_SCREEN_NAME_URL);
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
			response = httpclient.execute(httpPost);
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}catch(ClientProtocolException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
}
