package jp.jagfukuoka.sodefuri.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * Twitterの情報を保持するPreferences
 * 
 * @author shikajiro
 * 
 */
public class TwitterPreferences {
	// twitter consumer key & sercret
	// TODO 外部ファイル化
	public static final String CONSUMER_KEY = "OJLV3hMh1EAbkNIzjB3IqA";
	public static final String CONSUMER_SERCRET = "Go0XfTVbHjtbxo2Asl3fMCLoXH7idRkQmjMv7quaMcI";

	public static final String REQUEST_TOKEN = "shutter_request_token";
	public static final String REQUEST_TOKEN_SERCRET = "shutter_request_token_sercret";

	public static final String ACCESS_TOKEN = "shutter_access_token";
	public static final String ACCESS_TOKEN_SERCRET = "shutter_access_token_sercret";

	/**
	 * pinキーを入力するためにrequestTokenを保持する
	 * 
	 * @param token
	 * @param tokenSecret
	 */
	public static void storeRequestToken(Context context, String token, String tokenSecret) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = preferences.edit();
		editor.putString(REQUEST_TOKEN, token);
		editor.putString(REQUEST_TOKEN_SERCRET, tokenSecret);
		editor.commit();
	}
	
	public void clearRequestToken(Context context){
		SharedPreferences preferences = PreferenceManager
		.getDefaultSharedPreferences(context);
		Editor editor = preferences.edit();
		editor.putString(REQUEST_TOKEN, "");
		editor.putString(REQUEST_TOKEN_SERCRET, "");
		editor.commit();
	}

	/**
	 * 一時保存したrequestTokenを取得する。
	 * 
	 * @return
	 */
	public static String getRequestToken(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getString(REQUEST_TOKEN, "");
	}

	/**
	 * 一時保存したrequestTokenSercretを取得する。
	 * 
	 * @return
	 */
	public static String getRequestTokenSercret(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getString(REQUEST_TOKEN_SERCRET, "");
	}

	/**
	 * twitterのrequestTokenの有無をチェックする。
	 * 
	 * @return
	 */
	public static boolean isRequestToken(Context context) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		String requestToken = preferences.getString(REQUEST_TOKEN, "");
		String requestTokenSercret = preferences.getString(
				REQUEST_TOKEN_SERCRET, "");
		if (requestToken != null && requestToken.length() > 0) {
			if (requestTokenSercret != null && requestTokenSercret.length() > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 永続化されているaccess_tokenを取得する。
	 * 
	 * @return
	 */
	public static String getAccessToken(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getString(ACCESS_TOKEN, "");
	}

	/**
	 * 永続化されているaccess_tokenを取得する。
	 * 
	 * @return
	 */
	public static String getAccessTokenSercret(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getString(ACCESS_TOKEN_SERCRET, "");
	}

	/**
	 * twitterのtokenを設定する。
	 * 
	 * @param accessToken
	 * @param accessTokenSercret
	 */
	public static void storeAccessToken(Context context, String accessToken,String accessTokenSercret) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = preferences.edit();
		editor.putString(ACCESS_TOKEN, accessToken);
		editor.putString(ACCESS_TOKEN_SERCRET, accessTokenSercret);
		editor.commit();
	}
	
	public static void clearAccessToken(Context context){
		SharedPreferences preferences = PreferenceManager
		.getDefaultSharedPreferences(context);
		Editor editor = preferences.edit();
		editor.putString(ACCESS_TOKEN, "");
		editor.putString(ACCESS_TOKEN_SERCRET, "");
		editor.commit();
	}

	/**
	 * twitterのtokenの有無をチェックする。
	 * 
	 * @return
	 */
	public static boolean isAccessToken(Context context) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		String accessToken = preferences.getString(ACCESS_TOKEN, "");
		String accessTokenSercret = preferences.getString(ACCESS_TOKEN_SERCRET, "");
		if (accessToken != null && accessToken.length() > 0) {
			if (accessTokenSercret != null && accessTokenSercret.length() > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * twitterのスクリーンネームをpreferenceに登録する。
	 * 
	 * @param screen_name
	 */
	public static void storeScreenName(Context context, String screen_name) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = preferences.edit();
		editor.putString("pre_twitter_name", screen_name);
		editor.commit();
	}

}
