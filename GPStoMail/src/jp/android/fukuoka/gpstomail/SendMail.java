package jp.android.fukuoka.gpstomail;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.util.Log;

public class SendMail extends Activity {
	private boolean DebugMode = false;
	private String mBody;
	private NotificationManager mNM;
	private Notification notification;
	private String mUser;
	private String mPassword;
	private MediaPlayer mp;
	private Context context;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        //Notificationの準備
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		Bundle extras = getIntent().getExtras();
		mBody =new String();
		if (extras != null) {
			mBody = (String) extras.getCharSequence("mBody");
		}
		//音声再生
		mp = MediaPlayer.create(this, R.raw.alarm);
		mp.start();
		context = this;

		//Mail Sending
		if(DebugMode){
			Log.i("GPStoMail","Mail Sending");
			Log.i("GPStoMail", setting.getTextbyKey("Gmail_account", context));
			Log.i("GPStoMail", setting.getTextbyKey("Gmail_password", context));
			Log.i("GPStoMail", setting.getTextbyKey("Mail_Subject", context).toString());
			Log.i("GPStoMail", setting.getTextbyKey("fromMail_adr", context));
			Log.i("GPStoMail", setting.getTextbyKey("toMail_adr", context));
			Log.i("GPStoMail", mBody.toString());
		}
		mUser = setting.getTextbyKey("Gmail_account", context);
		mPassword =setting.getTextbyKey("Gmail_password", context);
		
    	SendMail_mod(setting.getTextbyKey("Mail_Subject", context),
    			mBody.toString(),
    			setting.getTextbyKey("fromMail_adr", context),
    			setting.getTextbyKey("toMail_adr", context));
    	finish();
    	//Activity を非表示へセット
//		moveTaskToBack(true);
	};
	
	protected void SendMail_mod(String subject, String body, String sender, String recipient){
		GMailSender G_sender = new GMailSender(mUser,mPassword);

		try {
			G_sender.sendMail(subject,
					body,
					sender,
					recipient);
			// Notificationの表示
			notification = new Notification(R.drawable.icon, getText(R.string.app_name),System.currentTimeMillis());
			PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
					new Intent(this, jp.android.fukuoka.gpstomail.Main.class),
					Intent.FLAG_ACTIVITY_NEW_TASK);
			notification.setLatestEventInfo(this,
					getText(R.string.app_name), 
					getText(R.string.Succeed_Message_Gmail_msg), contentIntent);
			mNM.notify(R.string.app_name, notification);

		} catch (Exception e) {
			// Notificationの表示
			notification = new Notification(R.drawable.icon, getText(R.string.app_name),System.currentTimeMillis());
			PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
					new Intent(this, jp.android.fukuoka.gpstomail.Main.class),
					Intent.FLAG_ACTIVITY_NEW_TASK);
			notification.setLatestEventInfo(this,
					getText(R.string.app_name), 
					getText(R.string.Error_Message_Gmail_msg), contentIntent);
			mNM.notify(R.string.app_name, notification);
			
			Log.e("SendMail", e.getMessage(), e);
		}
	}
}
