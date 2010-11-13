package jp.android.fukuoka.gpstomail;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class setting extends PreferenceActivity{
	protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        addPreferencesFromResource(R.xml.alarm_preferences);
	    }
	}