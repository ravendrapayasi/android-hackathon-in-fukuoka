package jp.jagfukuoka.eightbit;

import jp.jagfukuoka.eightbit.Sound.DemoMusic;
import jp.jagfukuoka.eightbit.Sound.ElectronicSound;
import jp.jagfukuoka.eightbit.Sound.Saw;
import jp.jagfukuoka.eightbit.Sound.Square;
import jp.jagfukuoka.eightbit.Sound.Triangle;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class Buttons extends Activity {
			static String TAG = "eightbit";
			
			private static AudioMachine audio;	
			
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
		        c5Button.setText("Éh");
		        c5Button.setMinEms(8);
		        Button d5Button = new Button(this);
		        d5Button.setText("Éå");
		        d5Button.setMinEms(8);
		        Button e5Button = new Button(this);
		        e5Button.setText("É~");
		        e5Button.setMinEms(8);
		        Button f5Button = new Button(this);
		        f5Button.setText("ÉtÉ@");
		        f5Button.setMinEms(8);
		        Button g5Button = new Button(this);
		        g5Button.setText("É\");
		        g5Button.setMinEms(8);
		        Button a5Button = new Button(this);
		        a5Button.setText("Éâ");
		        a5Button.setMinEms(8);
		        Button b5Button = new Button(this);
		        b5Button.setText("ÉV");
		        b5Button.setMinEms(8);
		        Button c6Button = new Button(this);
		        c6Button.setText("Éh");
		        c6Button.setMinEms(8);

		        Button demoButton = new Button(this);
		        demoButton.setText("DEMO");
		        demoButton.setMinEms(8);
		        
		        
		        Button stopButton = new Button(this);
		        stopButton.setText("í‚é~");
		        
		        Button exitButton = new Button(this);
		        exitButton.setText("èIóπ");
		        
		        Button triangleButton = new Button(this);
		        triangleButton.setText("éOäpîg");
		        triangleButton.setMinEms(6);
		        Button squareButton = new Button(this);
		        squareButton.setText("ãÈå`îg");
		        squareButton.setMinEms(6);
		        Button sawButton = new Button(this);
		        sawButton.setText("ÇÃÇ±Ç¨ÇËîg");
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
		        
		        LinearLayout layout5 = new LinearLayout(this);
		        layout5.setOrientation(LinearLayout.HORIZONTAL);
		        layout5.addView(demoButton);
		                
		        //LinearLayout
		        LinearLayout linearLayout = new LinearLayout(this);
		        linearLayout.setOrientation(LinearLayout.VERTICAL);
		        linearLayout.addView(typeHorizontalLayout);
		        linearLayout.addView(layout1);
		        linearLayout.addView(layout2);
		        linearLayout.addView(layout3);
		        linearLayout.addView(layout4);                
		        linearLayout.addView(layout5);                
		        
		        linearLayout.addView(stopButton);
		        linearLayout.addView(exitButton);
		        setContentView(linearLayout);        
		                
		        c5Button.setOnClickListener( createSoundOnClickLisner(50, C5, 1.0f));
		        d5Button.setOnClickListener( createSoundOnClickLisner(50, D5, 1.0f));
		        e5Button.setOnClickListener( createSoundOnClickLisner(50, E5, 1.0f));
		        f5Button.setOnClickListener( createSoundOnClickLisner(50, F5, 1.0f));
		        g5Button.setOnClickListener( createSoundOnClickLisner(50, G5, 1.0f));
		        a5Button.setOnClickListener( createSoundOnClickLisner(50, A5, 1.0f));
		        b5Button.setOnClickListener( createSoundOnClickLisner(50, B5, 1.0f));
		        c6Button.setOnClickListener( createSoundOnClickLisner(50, C6, 1.0f));
		        demoButton.setOnClickListener( createDemoOnClickLisner());
		        
		        stopButton.setOnClickListener(new View.OnClickListener() {
		            @Override
		            public void onClick(View v) {            	
						if (audio != null){
							audio.Stop();
						}             	
		            }
		        });              
		        exitButton.setOnClickListener(new View.OnClickListener() {
		            @Override
		            public void onClick(View v) {            	
		            	finish();
		            }
		        });              
		    }   
		       
		    @Override
		    protected void onPause() {    
		    	super.onPause();
		    	if(audio!=null){
		    		audio.Stop();
		    	}
		    }    
		    public OnClickListener createDemoOnClickLisner(){
		    	return new View.OnClickListener() {
		            @Override
		            public void onClick(View v) {            	
		            	Log.w("hoge","onc");
						audio = new AudioMachine();
						DemoMusic sound = new DemoMusic();
						audio.Play(sound);            				
		           }    	
		    	};
		    }
		    public OnClickListener createSoundOnClickLisner(final int length,final float onkai,final float volume){
		    	return new View.OnClickListener() {
		            @Override
		            public void onClick(View v) {            	
		    				audio = new AudioMachine();
		    				float newVolume = volume;
		    				ElectronicSound sound;
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
		    	};
		    }
		 
}
