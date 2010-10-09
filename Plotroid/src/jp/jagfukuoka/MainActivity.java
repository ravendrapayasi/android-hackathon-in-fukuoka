package jp.jagfukuoka;

import com.googlecode.chartdroid.core.IntentConstants;

import jp.jagfukuoka.provider.DataContentProvider;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final Button button = (Button) findViewById(R.id.ok);
        SpannableStringBuilder sb = (SpannableStringBuilder)((EditText)findViewById(R.id.entry)).getText();
        button.setTag(sb.toString());
        button.setOnClickListener(new TwitterAPIListener() {
        
        });
        
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String account = sharedPreferences.getString("account", "");
        String password = sharedPreferences.getString("password", "");
    }
}