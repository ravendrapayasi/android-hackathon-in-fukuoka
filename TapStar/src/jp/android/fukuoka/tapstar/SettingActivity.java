package jp.android.fukuoka.tapstar;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;

public class SettingActivity extends PreferenceActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("code_log","onCreate");
		addPreferencesFromResource(R.layout.setting);
	}
}
