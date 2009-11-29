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

public class TegakyActivity extends Activity {
	TegakyView tegakyView;

//	private String mTempFilePath = Environment.getExternalStorageDirectory()+"/hoge.jpg";
	private String mTempFilePath = Environment.getExternalStorageDirectory()+"/temp.jpg";
	
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
    
	// http://www.saturn.dti.ne.jp/npaka/android/SnapShotEx/index.html
    
	protected void writeFile(Bitmap bitmap) {
        //ファイルに保存
        try {
            byte[] w=bmp2data(bitmap,Bitmap.CompressFormat.JPEG,80);
//            writeDataFile("temp.jpg",w);
          writeDataFile(mTempFilePath,w);
        } catch (Exception e) {
            android.util.Log.e("",e.toString());
        }
	}
	
	
    //Bitmap→バイトデータ
    private static byte[] bmp2data(Bitmap src,
        Bitmap.CompressFormat format,int quality) {
        ByteArrayOutputStream os=new ByteArrayOutputStream();
        src.compress(format,quality,os);            
        return os.toByteArray();
    }
   
    //ファイルへのバイトデータ書き込み
    private void writeDataFile(String name,byte[] w) throws Exception {
        OutputStream out=null;
        try {
            out=openFileOutput(name,Context.MODE_WORLD_READABLE);
            out.write(w,0,w.length);
            out.close();
        } catch (Exception e) {
            try {
                if (out!=null) out.close();
            } catch (Exception e2) {
            }
            throw e;
        }
    }


	private void doIntent() {

//		String mTempFilePath = "/sdcard/PicSay/picsay-1258543652.jpg";
//		String mTempFilePath = "/sdcard/hoge.jpg";

		/*
		Intent intent = new Intent(Intent.ACTION_SENDTO);
		
		intent.putExtra(Intent.EXTRA_EMAIL, "teknocat@gmail.com");
		intent.putExtra(Intent.EXTRA_SUBJECT, "title");
		intent.putExtra(Intent.EXTRA_TEXT, "body"); 
*/
//		Intent intent = new Intent(Intent.ACTION_SENDTO,
//				Uri.fromParts("mailto", "teknocat@gmail.com", null));

		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"teknocat@gmail.com"}); 
//		intent.setDataAndType(Uri.fromFile(new File(mTempFilePath)), "image/jpeg");
//		intent.setData(Uri.fromFile(new File(mTempFilePath)));
		intent.setType("jpeg/image");
		intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(mTempFilePath)));
		intent.putExtra(Intent.EXTRA_SUBJECT, "title");
		intent.putExtra(Intent.EXTRA_TEXT, "body"); 

		startActivity(intent);
	}
    
  
 }