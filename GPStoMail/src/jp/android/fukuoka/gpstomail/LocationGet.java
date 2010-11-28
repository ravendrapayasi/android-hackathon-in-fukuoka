package jp.android.fukuoka.gpstomail;

import java.util.Date;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
//import android.widget.Button;
//import android.widget.TextView;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class LocationGet extends Service implements LocationListener{
	private boolean DebugMode = false;

	private LocationManager lm;
	private String TAG = "GPS2MAIL";
//	private TextView mText1;
//	private TextView mText2;
//	private TextView mText3;
//	private TextView mText4;
//	private Button btn1;
	private Date mTime;
	private StringBuilder mBody;
	
	@Override
	public void onCreate() {
		super.onCreate();

	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
//		if(DebugMode){ 
//			setContentView(R.layout.gps);
//
//			mText1 = (TextView)findViewById(R.id.TextView01);
//			mText2 = (TextView)findViewById(R.id.TextView02);
//			mText3 = (TextView)findViewById(R.id.TextView03);
//			mText4 = (TextView)findViewById(R.id.TextView04);
//			mText4.setAutoLinkMask(Linkify.WEB_URLS);
//
//			btn1 = (Button) findViewById(R.id.Button02);
//			btn1.setOnClickListener(new View.OnClickListener() {
//				public void onClick(View v) {
//					finish();
//					// クリック時の処理
//					Intent i = new Intent(v.getContext(),setting.class);
//					startActivity(i);
//					onProviderDisabled(null);
//				}
//			});
//
//		}
		if(DebugMode){
			Log.i(TAG,"Start LocationGet.class");
		}

		mTime =new  Date();

		// LocationManagerでGPSの値を取得するための設定
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);   

		

		// 位置情報の更新を受け取るように設定
		this.onProviderEnabled(LocationManager.GPS_PROVIDER);
		this.onProviderEnabled(LocationManager.NETWORK_PROVIDER);
	}

	// GPSの値が更新されると呼び出される
	public void onLocationChanged(Location location) {
		if(DebugMode){
			Log.i(TAG,""+location.getLatitude());
			Log.i(TAG,""+location.getLongitude());
			mTime.setTime(location.getTime());
			if (mTime !=null ){
				//						mText3.setText(mTime.toLocaleString());
				Log.i(TAG,mTime.toLocaleString());
			}

			//			mText1.setText(""+location.getLatitude());
			//			mText2.setText(""+location.getLongitude());
			//			mText4.setText("http://maps.google.co.jp/maps?geocode=&sll="+location.getLatitude()+","+location.getLongitude()+"&q="+location.getLatitude()+","+location.getLongitude());
		}

		mBody = new StringBuilder();
		mBody.append(getText(R.string.Message_Longitude)+""+location.getLongitude()+"\n");
		mBody.append(getText(R.string.Massage_Latitude)+""+location.getLatitude()+"\n");
		mBody.append(getText(R.string.Massage_Altitude)+""+location.getAltitude()+"\n");
		mBody.append(getText(R.string.Message_Time) + mTime.toLocaleString()+"\n");
		mBody.append(getText(R.string.Message_Accuracy) +"" + location.getAccuracy()+"m(s)\n");

		mBody.append(getText(R.string.Message_Map_info));
		mBody.append("http://maps.google.co.jp/maps?geocode=&sll="+location.getLatitude()+","+location.getLongitude()+"&q="+location.getLatitude()+","+location.getLongitude());
		if(DebugMode){
			Log.i(TAG,mBody.toString());
		}

		//Mail作成
		Intent intent = new Intent(this.getBaseContext(),SendMail.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		CharSequence text = mBody.toString();
		intent.putExtra("mBody", text);
		startActivity(intent);
		onProviderDisabled(null);
		stopSelf();
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

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		onProviderDisabled(null);
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