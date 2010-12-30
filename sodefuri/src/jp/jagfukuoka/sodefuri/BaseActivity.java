package jp.jagfukuoka.sodefuri;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * 全てのactivityの基底クラス
 * @author shikajiro
 *
 */
public class BaseActivity extends Activity {
	//twitter consumer key & sercret
	// TODO 外部ファイル化
	public static final String CONSUMER_KEY = "OJLV3hMh1EAbkNIzjB3IqA";
	public static final String CONSUMER_SERCRET = "Go0XfTVbHjtbxo2Asl3fMCLoXH7idRkQmjMv7quaMcI";
	
	protected static final String REQUEST_TOKEN = "request_token";
	protected static final String REQUEST_TOKEN_SERCRET = "request_token_sercret";
	
	protected static final String ACCESS_TOKEN = "access_token";
	protected static final String ACCESS_TOKEN_SERCRET = "access_token_sercret";

	/**
	 * pinキーを入力するためにrequestTokenを保持する
	 * @param token
	 * @param tokenSecret
	 */
	protected void storeRequestToken(String token, String tokenSecret) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		Editor editor = preferences.edit();
		editor.putString(REQUEST_TOKEN, token);
		editor.putString(REQUEST_TOKEN_SERCRET, tokenSecret);
		editor.commit();
	}
	
	/**
	 * 一時保存したrequestTokenを取得する。
	 * @return
	 */
	protected String getRequestToken(){
		return PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(REQUEST_TOKEN, "");
	}
	/**
	 * 一時保存したrequestTokenSercretを取得する。
	 * @return
	 */
	protected String getRequestTokenSercret(){
		return PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(REQUEST_TOKEN_SERCRET, "");
	}
	
	/**
	 * twitterのtokenの有無をチェックする。
	 * @return
	 */
	protected boolean isAccessToken() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String accessToken = preferences.getString(ACCESS_TOKEN, "");
		String accessTokenSercret = preferences.getString(ACCESS_TOKEN_SERCRET, "");
		if(accessToken != null && accessToken.length() > 0){
			if(accessTokenSercret != null && accessTokenSercret.length() > 0){
				return true;
			}
		}
		return false;
	}
	/**
	 * twitterのrequestTokenの有無をチェックする。
	 * @return
	 */
	protected boolean isRequestToken() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String requestToken = preferences.getString(REQUEST_TOKEN, "");
		String requestTokenSercret = preferences.getString(REQUEST_TOKEN_SERCRET, "");
		if(requestToken != null && requestToken.length() > 0){
			if(requestTokenSercret != null && requestTokenSercret.length() > 0){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 永続化されているaccess_tokenを取得する。
	 * @return
	 */
	protected String getAccessToken(){
		return PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(ACCESS_TOKEN, "");
	}
	/**
	 * 永続化されているaccess_tokenを取得する。
	 * @return
	 */
	protected String getAccessTokenSercret(){
		return PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(ACCESS_TOKEN_SERCRET, "");
	}

	/**
	 * twitterのtokenを設定する。
	 * @param accessToken
	 * @param accessTokenSercret
	 */
	protected void storeAccessToken(String accessToken, String accessTokenSercret) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		Editor editor = preferences.edit();
		editor.putString("access_token", accessToken);
		editor.putString("access_token_sercret", accessTokenSercret);
		editor.commit();
	}
	
	/**
	 * twitterのスクリーンネームをpreferenceに登録する。
	 * @param screen_name
	 */
	protected void storeScreenName(String screen_name) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		Editor editor = preferences.edit();
		editor.putString("pre_twitter_name", screen_name);
		editor.commit();
	}
}
