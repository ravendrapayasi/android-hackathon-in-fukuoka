package jp.or.missusoft.hyakunin;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
//import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Poeme_HyakuninActivity extends Activity {
	
//	private EditText ed_poeme;
	private CharDrawViewMultiLine ed_poeme;

	private CharDrawView ed_writer;
//	private VTextView ed_writer;
	private int MaxCount = 0;
	private int CurCount = 0;
	
	private String[] poeme ;
	private String[] writer;
	
	private boolean Wait_flg = false;
	
	
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
        // c‘‚«ƒrƒ…[ƒ‚ÉÝ’è‚·‚é

        ed_poeme.setText(poeme[0].replace(" ", Character.toString((char) 0d)));
        ed_writer.setText(writer[0]);
        MaxCount = poeme.length;
        CurCount = 0;
        Wait_flg = true;
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
	        
	        // Timer ‚ÌÝ’è‚ð‚·‚é
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
    
}
