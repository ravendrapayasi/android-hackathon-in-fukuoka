package jp.android.fukuoka.tegaky;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import jp.android.fukuoka.tegaky.PictUtil;

public class TegakyActivity extends Activity {
	TegakyView tegakyView;

	private String mTempFilePath;
	
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
//			Toast.makeText(this, "height = "+tegakyView.getBitmap().getHeight()+" Width = "+tegakyView.getBitmap().getWidth(), Toast.LENGTH_SHORT).show();
			writeFile(tegakyView.getBitmap());
			doIntent();
		}
		return super.onKeyDown(keyCode, event);
	}
    
	protected void writeFile(Bitmap bitmap) {
		PictUtil.saveToCacheFile(bitmap);
		mTempFilePath = PictUtil.getCacheFilename();
	}

	private void doIntent() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"teknocat@gmail.com"}); 
		intent.setType("jpeg/image");
		intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(mTempFilePath)));
		intent.putExtra(Intent.EXTRA_SUBJECT, "title");
		intent.putExtra(Intent.EXTRA_TEXT, "body"); 

		startActivity(intent);
	}
    
  
 }