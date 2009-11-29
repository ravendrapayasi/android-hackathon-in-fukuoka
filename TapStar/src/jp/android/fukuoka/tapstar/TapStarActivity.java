package jp.android.fukuoka.tapstar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class TapStarActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		//Log.i("code_log","onCreateOptionsMenu");
		menu.add(0,0,0, "設定").setIcon(android.R.drawable.ic_menu_preferences);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);
		Log.i("code_log","onMenuItemSelected");
		switch (item.getItemId()) {
			case 0://設定
				//設定画面への遷移
				Log.i("code_log","Open TapStarActivity");
				startActivityForResult(new Intent(TapStarActivity.this,SettingActivity.class),0);
				break;
		}
		return true;
	}

}