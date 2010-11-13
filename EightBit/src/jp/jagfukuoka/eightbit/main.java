package jp.jagfukuoka.eightbit;

import jp.jagfukuoka.eightbit.Sound.*;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

public class main extends Activity {
	private static AudioMachine audio;	
	static String TAG = "eightbit";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
                        
        Button playButton = new Button(this);
        playButton.setText("çƒê∂");
        Button stopButton = new Button(this);
        stopButton.setText("í‚é~");
        
        //LinearLayout
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(playButton);
        linearLayout.addView(stopButton);
        setContentView(linearLayout);        
        
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {            	
            	new Thread(new Runnable() {
            			@Override
            		    public void run() {
            				audio = new AudioMachine();
            				Sound sound = new Sin();
            				sound.createAudio(5,400);
            				audio.Play(sound);
            				
            		    }
            	}).start();
             	
            }
        });
        
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {            	
            	new Thread(new Runnable() {
            			@Override
            		    public void run() {
            				if (audio != null){
            					audio.Stop();
            				}
            		    }
            	}).start();
             	
            }
        });              
    }   
       
    @Override
    protected void onPause() {    
    	super.onPause();
    	audio.Stop();
    }    

}