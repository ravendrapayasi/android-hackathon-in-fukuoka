package jp.jagfukuoka.eightbit;

import jp.jagfukuoka.eightbit.Sound.*;
import jp.jagfukuoka.eightbit.musicalscore.Note;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;

public class Telmin extends Activity {
    @Override
    protected void onPause() {    
    	super.onPause();
    	if(audioMachine!=null){
    		audioMachine.Stop();
    	}
    }    

    int width;
    int height;
	@Override
	public void onCreate(Bundle savedInstanceState){
	    super.onCreate(savedInstanceState);
	    WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
	    Display display = wm.getDefaultDisplay();
	    width = display.getWidth();
	    height =  display.getHeight();
	    
	    setContentView(R.layout.telmin);
	    xText = (TextView)findViewById(R.id.XText);
	    yText = (TextView)findViewById(R.id.YText);
	    audioMachine = new AudioMachine();
	    sound = new Sin();
		if(isPerformance){
		    sound.createAudio(0, Note.C5, 0);
			this.audioMachine.SetVolume(0f);
			audioMachine.Play(sound);
		}
	}
	private TextView xText;
	private TextView yText;
	AudioMachine audioMachine;
	ElectronicSound sound;
	
	float y;
	float x;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    switch (event.getAction()) {
	    case MotionEvent.ACTION_DOWN:
			this.audioMachine.SetVolume(1f);
	    	y = event.getY();
	    	x = event.getX();
	    	if(!isDelay){
		    	playSound(x,y);
		    	if(!isPerformance){
		   		    audioMachine.Play(sound);
		    	}
		    	loopEngine.start();
	    	}

	    	isDelay = false;
	    	volume = 10;
	    	
	    	break;
	    case MotionEvent.ACTION_UP:
	    	isDelay = true;
	    	break;
	    case MotionEvent.ACTION_MOVE:
	    	if(isDelay){
				this.audioMachine.SetVolume(1f);
	    	}
	    	y = event.getY();
	    	x = event.getX();

	    	isDelay = false;
	    	volume = 10;
	    	
		    break;
	    case MotionEvent.ACTION_CANCEL:
	    	break;
	    }
	    
	    return super.onTouchEvent(event);
	}
	private boolean isDelay = false;
	private int volume = 10;
	private boolean isPerformance = false;
	private void update(){
		// TouchEventのタイミングで波形を作ると負荷が大きいため、
		// 一定時間ごとに処理をまるめる
		if(isDelay){
			volume--;
			if(volume<=0){
				isDelay = false;
				if(isPerformance){
					sound.createAudio(0, 440f, 0f);
				}else{
					sound.stop();
				}
				
				loopEngine.stop();
			}else{
				this.audioMachine.SetVolume((float)volume/10);
			}
			
		}else{
			playSound(x,y);
		}
	}
	private void playSound(float x, float y){
		int ix=(int)x;
		int iy=(int)y;
		float volume = (float)ix/width;
		float interval = ((float)(height-iy))/height*1000;
    	xText.setText(String.valueOf(volume));
    	yText.setText(String.valueOf(interval)+"hz");
		
		sound.createAudio(0, interval, volume);

	}
    //一定時間後にupdateを呼ぶためのオブジェクト
    class LoopEngine extends Handler {
    	private boolean isUpdate;
    	public void start(){
    		this.isUpdate = true;
    		handleMessage(new Message());
    	}
    	public void stop(){
    		this.isUpdate = false;
    	}
        
    	
    	@Override
    	public void handleMessage(Message msg) {
    		this.removeMessages(0);//既存のメッセージは削除
    		if(this.isUpdate){
        		Telmin.this.update();//自身が発したメッセージを取得してupdateを実行
    			sendMessageDelayed(obtainMessage(0), 10);
    		}
    	} 
    };
    private LoopEngine loopEngine = new LoopEngine();	

}
