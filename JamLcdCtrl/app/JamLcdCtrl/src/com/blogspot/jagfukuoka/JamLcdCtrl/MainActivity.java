package com.blogspot.jagfukuoka.JamLcdCtrl;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		Button b1 = (Button) findViewById(R.id.BtnSendData);
		b1.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				int ret = sendData(0);
				Log.d("send", "result:" + ret);
			}
		});    
    }
    
    //////////////////////////////////////////
    // JNI
    //////////////////////////////////////////
    
    static {
        // ライブラリをロードします
        System.loadLibrary("jamlcdctrl");
    }

    static public native int sendData(int ctrl);
}