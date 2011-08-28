package jp.or.missusoft.hyakunin;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
//import java.io.IOException;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Poeme_HyakuninActivity extends Activity implements OnClickListener,OnInitListener {
	
//	private EditText ed_poeme;
	private CharDrawViewMultiLine ed_poeme;

	private CharDrawView ed_writer;
//	private VTextView ed_writer;
	private Button Speech_btn;
	
	private TextToSpeech tts;
	private int MaxCount = 0;
	private int CurCount = 0;
	
	private String[] poeme ;
	private String[] writer;
	private String[] poemeToRead;
	
	private boolean Wait_flg = false;
	static String tag ="Poeme_HyakuninActivity";
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ed_poeme = (CharDrawViewMultiLine) findViewById(R.id.poeme);
        ed_writer =(CharDrawView) findViewById(R.id.writer);
//        ed_writer = (VTextView)findViewById(R.id.writer);

        poeme = getResources().getStringArray(R.array.Poemes);
        writer = getResources().getStringArray(R.array.writers);
        poemeToRead=getResources().getStringArray(R.array.PoemesToRead);
        // ècèëÇ´ÉrÉÖÅ[ÉèÇ…ê›íËÇ∑ÇÈ

        ed_poeme.setText(poeme[0].replace(" ", Character.toString((char) 0d)));
        ed_writer.setText(writer[0]);
        MaxCount = poeme.length;
        CurCount = 0;
        Wait_flg = true;
        
        Speech_btn =(Button) findViewById(R.id.buttonSpeech);
        Speech_btn.setOnClickListener(this);
        
        tts = new TextToSpeech(getApplicationContext(), this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        tts.shutdown();
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	
    	if (Wait_flg){
    		Wait_flg = false;
	    	CurCount=CountNext(CurCount);
	        ed_poeme.setText(poeme[CurCount].replace(" ", Character.toString((char) 0d)));
	        ed_poeme.invalidate();
	        ed_writer.setText(writer[CurCount]);
	        ed_writer.invalidate();
	        
	        // Timer ÇÃê›íËÇÇ∑ÇÈ
	        Timer timer = new Timer(false);
	        timer.schedule(new TimerTask() {
	            public void run() {
	            	Wait_flg = true;
	                };
	        },1000);
    	}
        
        return true;
    }

    private int CountNext(int Value){
    	if (Value + 1 >= MaxCount){
    		Value = 0;
    	}else{
    		Value += 1;
    	}
		return Value;
    }
    
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
        	Locale locale = Locale.JAPANESE;
            if (tts.isLanguageAvailable(locale) >= TextToSpeech.LANG_AVAILABLE) {
                tts.setLanguage(locale);
                tts.speak("ê›íËäÆóπ", TextToSpeech.QUEUE_FLUSH, null);
            } else {
				Log.e(tag, "Error SetLocale");
                tts.speak("I'm not ready!", TextToSpeech.QUEUE_FLUSH, null);
            }
            
        } else {
        	Log.e(tag,"Oops!");
        }
    }
    
    @Override
    public void onClick(final View v) {
        tts.speak(poemeToRead[CurCount].toString(), TextToSpeech.QUEUE_FLUSH, null);
    }
        
    
}
