package jp.jagfukuoka.eightbit;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.graphics.Shader.TileMode;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Menu extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        
	    final Button buttonsMode = (Button)findViewById(R.id.ButtonsMode);
	    buttonsMode.setOnClickListener(new OnClickListener() {
	    	@Override
			public void onClick(View v) {
	    		Intent i = new Intent(getApplicationContext(),Buttons.class);
	    		startActivity(i);
	    	}
	    });
	    final Button telminMode = (Button)findViewById(R.id.ThelminMode);
	    telminMode.setOnClickListener(new OnClickListener(){
	    	@Override
	    	public void onClick(View v){
	    		Intent i = new Intent(getApplicationContext(),Telmin.class);
	    		startActivity(i);
	    	}
	    });


    }
}
