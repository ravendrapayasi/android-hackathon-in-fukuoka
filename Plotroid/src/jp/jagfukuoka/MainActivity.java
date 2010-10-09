package jp.jagfukuoka;

import com.googlecode.chartdroid.core.IntentConstants;

import jp.jagfukuoka.provider.DataContentProvider;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final Button button = (Button) findViewById(R.id.ok);
        button.setOnClickListener(new TwitterAPIListener() {
        
        });
    }
}