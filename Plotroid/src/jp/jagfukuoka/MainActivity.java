package jp.jagfukuoka;

import android.app.Activity;
import android.app.AlertDialog;
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
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	// ダイアログの表示
            	AlertDialog.Builder dlg;
            	dlg = new AlertDialog.Builder(MainActivity.this);
            	dlg.setTitle("TEST");
            	dlg.setMessage("Hello, World!");
            	dlg.setPositiveButton("OK", null);
            	dlg.show();
            }
        });
    }
}