package jp.android.fukuoka.gpstomail;

import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
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
    private String TAG = "GPS2MAIL";
    private TextView mText1;
    private TextView mText2;
    private TextView mText3;
    private TextView mText4;
    private Date mTime;
    
    private Button btn1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps);
        mText1 = (TextView)findViewById(R.id.TextView01);
        mText2 = (TextView)findViewById(R.id.TextView02);
        mText3 = (TextView)findViewById(R.id.TextView03);
        mText4 = (TextView)findViewById(R.id.TextView04);
        mTime =new  Date();
        mText4.setAutoLinkMask(Linkify.WEB_URLS);        
        // LocationManagerでGPSの値を取得するための設定
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);    
        // 値が変化した際に呼び出されるリスナーの追加
//        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
//        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

        // 位置情報の更新を受け取るように設定
        this.onProviderEnabled(LocationManager.GPS_PROVIDER);
        this.onProviderEnabled(LocationManager.NETWORK_PROVIDER);

        btn1 = (Button) findViewById(R.id.Button02);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // クリック時の処理
            	Intent i = new Intent(v.getContext(),setting.class);
            	startActivity(i);
            	onProviderDisabled(null);
            }
        });
        
    }
    @Override
    public void onStart() {
     super.onStart();
     
    }

    @Override
    public void onStop() {
    super.onStop();

    }
    
    // GPSの値が更新されると呼び出される
    public void onLocationChanged(Location location) {
        Log.i(TAG,""+location.getLatitude());
        Log.i(TAG,""+location.getLongitude());
        Log.i(TAG,""+location.getTime());
        mText1.setText(""+location.getLatitude());
        mText2.setText(""+location.getLongitude());
        mTime.setTime(location.getTime());
        if (mTime !=null ){
        	mText3.setText(mTime.toLocaleString());
        }
//        mText4.setText("geo:"+location.getLongitude()+","+location.getLatitude());
//        mText4.setText("http://maps.google.co.jp/maps?f=q&source=s_q&hl=ja&geocode=&sll="+location.getLongitude()+","+location.getLatitude());
        mText4.setText("http://maps.google.co.jp/maps?geocode=&sll="+location.getLongitude()+","+location.getLatitude());
        onProviderDisabled(null);
    }

    public void onProviderDisabled(String provider) {
    	// 位置情報の更新を止める
    	lm.removeUpdates(this);

    }

    public void onProviderEnabled(String provider) {
    	lm.requestLocationUpdates(provider, 0, 0, this);
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
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