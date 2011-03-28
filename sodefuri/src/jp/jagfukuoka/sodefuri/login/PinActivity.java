package jp.jagfukuoka.sodefuri.login;

import jp.jagfukuoka.sodefuri.R;
import jp.jagfukuoka.sodefuri.RecentListActivity;
import jp.jagfukuoka.sodefuri.server.twitter.TwitterRequest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

/**
 * PinCode入力画面
 * @author shikajiro
 *
 */
public class PinActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pin_input);
		
		View pinButton = findViewById(R.id.PinButton);
		pinButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String pincode = ((EditText) findViewById(R.id.PinText)).getText().toString();
				TwitterRequest.setPinCode(getApplicationContext(), pincode);
				startActivity(new Intent(getApplicationContext(),RecentListActivity.class));
			}

		});
	}
}
