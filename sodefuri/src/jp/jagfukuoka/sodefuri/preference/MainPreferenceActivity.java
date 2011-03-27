package jp.jagfukuoka.sodefuri.preference;

import jp.jagfukuoka.sodefuri.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * 設定画面
 * @author shikajiro
 *
 */
public class MainPreferenceActivity extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.pref);
	}
}
