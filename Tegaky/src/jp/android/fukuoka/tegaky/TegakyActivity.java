package jp.android.fukuoka.tegaky;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class TegakyActivity extends Activity {
	TegakyView tegakyView;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        tegakyView = new TegakyView(this);
        setContentView(tegakyView);
    }

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getAction()){
		case MotionEvent.ACTION_MOVE:
    		tegakyView.setNextPoint(event.getX(), event.getY());
    		tegakyView.invalidate();
    		break;
		case MotionEvent.ACTION_DOWN:
    		tegakyView.setFirstPoint(event.getX(), event.getY());
    		tegakyView.invalidate();
    		break;
		default:
			break;
    	}

		return super.onTouchEvent(event);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_DPAD_CENTER){
			Toast.makeText(this, "height = "+tegakyView.getBitmap().getHeight()+" Width = "+tegakyView.getBitmap().getWidth(), Toast.LENGTH_SHORT).show();
		}
		return super.onKeyDown(keyCode, event);
	}
    
 }