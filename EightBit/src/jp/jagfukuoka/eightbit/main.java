package jp.jagfukuoka.eightbit;

import jp.jagfukuoka.eightbit.Sound.*;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class main extends Activity {
	static String TAG = "eightbit";
	
	private static AudioMachine audio;	
	
	private static float volume;	
	private static int length;	
	private static int type = 1;
	
	public final static float C5 = 523.25f;
    public final static float D5 = 587.33f;
    public final static float E5 = 659.26f;
    public final static float F5 = 698.46f;
    public final static float G5 = 783.99f;
    public final static float A5 = 880.00f;
    public final static float B5 = 987.77f;
    public final static float C6 = 1046.50f;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
                        
        Button c5Button = new Button(this);
        c5Button.setText("ƒh");
        c5Button.setMinEms(8);
        Button d5Button = new Button(this);
        d5Button.setText("ƒŒ");
        d5Button.setMinEms(8);
        Button e5Button = new Button(this);
        e5Button.setText("ƒ~");
        e5Button.setMinEms(8);
        Button f5Button = new Button(this);
        f5Button.setText("ƒtƒ@");
        f5Button.setMinEms(8);
        Button g5Button = new Button(this);
        g5Button.setText("ƒ\");
        g5Button.setMinEms(8);
        Button a5Button = new Button(this);
        a5Button.setText("ƒ‰");
        a5Button.setMinEms(8);
        Button b5Button = new Button(this);
        b5Button.setText("ƒV");
        b5Button.setMinEms(8);
        Button c6Button = new Button(this);
        c6Button.setText("ƒh");
        c6Button.setMinEms(8);
        
        Button stopButton = new Button(this);
        stopButton.setText("’âŽ~");
        
        Button triangleButton = new Button(this);
        triangleButton.setText("ŽOŠp”g");
        triangleButton.setMinEms(6);
        Button squareButton = new Button(this);
        squareButton.setText("‹éŒ`”g");
        squareButton.setMinEms(6);
        Button sawButton = new Button(this);
        sawButton.setText("‚Ì‚±‚¬‚è”g");
        sawButton.setMinEms(6);
                
        triangleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	type = 1;
            }
        });
        
        squareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	type = 2;
            }
        }); 
        
        sawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	type = 3;
            }
        }); 
        
        
        LinearLayout typeHorizontalLayout = new LinearLayout(this);
        typeHorizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
        typeHorizontalLayout.addView(triangleButton);
        typeHorizontalLayout.addView(squareButton);
        typeHorizontalLayout.addView(sawButton);
        
        LinearLayout layout1 = new LinearLayout(this);
        layout1.setOrientation(LinearLayout.HORIZONTAL);
        layout1.addView(c5Button);
        layout1.addView(d5Button);
        
        LinearLayout layout2 = new LinearLayout(this);
        layout2.setOrientation(LinearLayout.HORIZONTAL);
        layout2.addView(e5Button);
        layout2.addView(f5Button);
        
        LinearLayout layout3 = new LinearLayout(this);
        
        layout3.setOrientation(LinearLayout.HORIZONTAL);
        layout3.addView(g5Button);
        layout3.addView(a5Button);
        
        LinearLayout layout4 = new LinearLayout(this);
        layout4.setOrientation(LinearLayout.HORIZONTAL);
        layout4.addView(b5Button);
        layout4.addView(c6Button);
                
        //LinearLayout
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(typeHorizontalLayout);
        linearLayout.addView(layout1);
        linearLayout.addView(layout2);
        linearLayout.addView(layout3);
        linearLayout.addView(layout4);                
        
        linearLayout.addView(stopButton);
        setContentView(linearLayout);        
                
        c5Button.setOnClickListener( createSoundOnClickLisner(5, C5, 1.0f));
        d5Button.setOnClickListener( createSoundOnClickLisner(5, D5, 1.0f));
        e5Button.setOnClickListener( createSoundOnClickLisner(5, E5, 1.0f));
        f5Button.setOnClickListener( createSoundOnClickLisner(5, F5, 1.0f));
        g5Button.setOnClickListener( createSoundOnClickLisner(5, G5, 1.0f));
        a5Button.setOnClickListener( createSoundOnClickLisner(5, A5, 1.0f));
        b5Button.setOnClickListener( createSoundOnClickLisner(5, B5, 1.0f));
        c6Button.setOnClickListener( createSoundOnClickLisner(5, C6, 1.0f));
        
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

    public OnClickListener createSoundOnClickLisner(final int length,final float onkai,final float volume){
    	return new View.OnClickListener() {
            @Override
            public void onClick(View v) {            	
            	new Thread(new Runnable() {
            			@Override
            		    public void run() {
            				audio = new AudioMachine();
            				float newVolume = volume;
            				Sound sound;
            				if (type == 1){
            					sound = new Triangle();            					
            				} else if (type == 2){
            					sound = new Square();
            					newVolume *= 0.3;
            				} else {
            					sound = new Saw();
            					newVolume *= 0.3;
            				}
            				sound.createAudio(length,onkai,newVolume);
            				audio.Play(sound);            				
            		    }
            	}).start();             	
           }    	
    	};
    }
    
}