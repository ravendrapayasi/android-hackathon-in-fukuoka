package jp.android.fukuoka.gpstomail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity implements OnClickListener{
	private Button btn1;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        btn1 = (Button) findViewById(R.id.Button01);
        btn1.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        // ƒNƒŠƒbƒN‚Ìˆ—
    	Intent i = new Intent(this,setting.class);
    	startActivity(i);
    }
}