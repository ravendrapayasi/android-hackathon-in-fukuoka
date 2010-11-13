package jp.android.fukuoka.gpstomail;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;

public class LocationGet extends Activity implements LocationListener{
    /** Called when the activity is first created. */
    private LocationManager lm;
    private String TAG = "GPS-App";
    private TextView mText1;
    private TextView mText2;
    private TextView mText3;
    private Button btn1;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mText1 = (TextView)findViewById(R.id.TextView01);
        mText2 = (TextView)findViewById(R.id.TextView02);
        mText3 = (TextView)findViewById(R.id.TextView03);
        mText3.setAutoLinkMask(Linkify.WEB_URLS);        
        // LocationManagerでGPSの値を取得するための設定
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);    
        // 値が変化した際に呼び出されるリスナーの追加
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        
        btn1 = (Button) findViewById(R.id.Button01);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // クリック時の処理
            	Intent i = new Intent(v.getContext(),setting.class);
            	startActivity(i);
            }
        });
        
    }
    
    // GPSの値が更新されると呼び出される
    public void onLocationChanged(Location location) {
        Log.i(TAG,""+location.getLatitude());
        Log.i(TAG,""+location.getLongitude());
        mText1.setText(""+location.getLatitude());
        mText2.setText(""+location.getLongitude());

        mText3.setText("http://maps.google.co.jp/maps?f=q&source=s_q&hl=ja&geocode=&sll="+location.getLongitude()+","+location.getLatitude());
    }

    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
        
    }

    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
        
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
        
    }

    /*
    public class GPSView extends View {
    	public GPSView(Context context) {
    		super(context);
    		setBackgroundColor(Color.WHITE);
    	}
    	@Override
    	protected void onDraw(Canvas canvas) {

    		Paint paint=new Paint();
    		paint.setAntiAlias(true);
    		paint.setTextSize(32);
    		canvas.drawText("LocationGet",320,200, paint);
    	}
    }
    */
}