package jp.jagfukuoka.simpletimer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class timer extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//XMLからViewを取得
        
        //XML上に定義したオブジェクトを取得
        countText = (TextView)findViewById(R.id.Counter);
        final Button startButton = (Button)findViewById(R.id.StartButton);
        startButton.setOnClickListener(new OnClickListener() {
	    	@Override
			public void onClick(View v) {
	    	    startDate =System.currentTimeMillis();
	    	    loopEngine.start();
	    	}
	    });
        final Button stopButton = (Button)findViewById(R.id.StopButton);
        stopButton.setOnClickListener(new OnClickListener() {
	    	@Override
			public void onClick(View v) {
	    		loopEngine.stop();
	    	}
	    });
        
    }
    private TextView countText; // カウントを表示するテキストボックス
    private long startDate;

    
    public void update(){
    	countText.setText(String.valueOf(System.currentTimeMillis()-startDate));//計測した時間と今の時間の差をカウンターに表示
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
        		timer.this.update();//自信が発したメッセージを取得してupdateを実行
    			sendMessageDelayed(obtainMessage(0), 10);//10ミリ秒後にメッセージを出力
    		}
    	} 
    };
    private LoopEngine loopEngine = new LoopEngine();
    
}