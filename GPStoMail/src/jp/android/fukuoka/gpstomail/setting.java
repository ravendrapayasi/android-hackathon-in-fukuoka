package jp.android.fukuoka.gpstomail;

import java.util.regex.Pattern;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class setting extends PreferenceActivity implements OnSharedPreferenceChangeListener{
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.alarm_preferences);
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		sp.registerOnSharedPreferenceChangeListener(this);
	}
	
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
		String pattern;
		String value; 
		if ((key.equals("toMail_adr")) || (key.equals("fromMail_adr")) ) {
			//メールアドレスチェック
			// Search for a valid mail pattern
			pattern = "[\\w\\.\\-]+@(?:[\\w\\-]+\\.)+[\\w\\-]+";
			value = sp.getString(key, null);
			if (!Pattern.matches(pattern, value)) {
				// The value is not a valid email address.
				// Do anything like advice the user or change the value

				// ダイアログの表示
				AlertDialog.Builder dlg;
				dlg = new AlertDialog.Builder(this);
				dlg.setTitle(R.string.War_Mailadr_invalid_title);
				dlg.setMessage(R.string.War_Mailadr_invalid_Cont);
				dlg.show();
			}
		}else if((key.equals("Gmail_account")) || (key.equals("Gmail_password")) ){
			// Search for a valid mail pattern
			if (key.equals("Gmail_password")){
				pattern = "[\\w\\W]{8,}$";
			}else{
				pattern = "[\\w\\W]{3,}$";
			}
			value = sp.getString(key, null);
			if (!Pattern.matches(pattern, value)) {
				// The value is not a valid email address.
				// Do anything like advice the user or change the value
				// ダイアログの表示
				AlertDialog.Builder dlg;
				dlg = new AlertDialog.Builder(this);
				dlg.setTitle(R.string.War_Gmail_invalid_title);
				dlg.setMessage(R.string.War_Gmail_invalid_Cont);

				dlg.show();
			}

		}else if(key.equals("mail_preference_key")){
			// Search for a valid mail pattern
			pattern = "/^([a-zA-Z0-9])+([a-zA-Z0-9\\._-])";
			value = sp.getString(key, null);
			if (!Pattern.matches(pattern, value)) {
				// The value is not a valid email address.
				// Do anything like advice the user or change the value
			}
		}
	}

	public static String getTextbyKey(String Key,Context context) {
		//キーで設定を読み出し
		return PreferenceManager.getDefaultSharedPreferences(context).getString(Key, null);
	}

}