package jp.android.fukuoka.AndroJanken;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AndroJanken extends Activity implements View.OnClickListener {

    /** Called when the activity is first created. */

    //メッセージ定数
    public static final int MSG_STATE_CHANGE=1;
    public static final int MSG_READ        =2;
	
	//Bluetooth
    private BluetoothAdapter     btAdapter;
    private BluetoothChatService chatService;

    //UI
    private TextView lblReceive;//受信ラベル
 
	   //リクエスト定数
    private static final int RQ_CONNECT_DEVICE=1;
    private static final int RQ_ENABLE_BT     =2;

	private Button   btnSend;   //送信ボタン
	   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        LinearLayout layout = (LinearLayout)this.findViewById(R.id.Layout01);
        btnSend=(Button) this.findViewById(R.id.Button01);
        //btnSend.setText("送信");
        btnSend.setOnClickListener(this);
        
        //Bluetoothアダプタ
        btAdapter=BluetoothAdapter.getDefaultAdapter();

        //受信ラベルの生成
        lblReceive=new TextView(this);
        lblReceive.setId(1);
        lblReceive.setText("");
        lblReceive.setTextSize(16.0f);
        lblReceive.setTextColor(Color.rgb(0,0,0));
        setLLParams(lblReceive,
            LinearLayout.LayoutParams.FILL_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);  
        layout.addView(lblReceive);    
    }
    
    
    //ライナーレイアウトのパラメータ指定
    private static void setLLParams(View view,int w,int h) {
        view.setLayoutParams(new LinearLayout.LayoutParams(w,h));
    }

	@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
        Intent serverIntent=new Intent(this,DeviceListActivity.class);
        startActivityForResult(serverIntent,RQ_CONNECT_DEVICE);
        	
	}
	
    //アプリ開始時に呼ばれる
    @Override
    public void onStart() {
        super.onStart();
        if (!btAdapter.isEnabled()) {
            Intent enableIntent = new Intent(
                BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent,RQ_ENABLE_BT);
        } else {
            if (chatService==null) chatService=
                new BluetoothChatService(this,handler);
        }
    }

    //アプリレジューム時に呼ばれる
    @Override
    public synchronized void onResume() {
        super.onResume();
        if (chatService!=null) {
            if (chatService.getState()==BluetoothChatService.STATE_NONE) {
                //Bluetoothの接続待ち(サーバ)
                chatService.start();
            }
        }
    }
	
    //アプリ復帰時に呼ばれる
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
  
    	
    	switch (requestCode) {
        //端末検索
        case RQ_CONNECT_DEVICE:
            if (resultCode==Activity.RESULT_OK) {
            	
            	String address=data.getExtras().
                    getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                
                //Bluetoothの接続要求(クライアント)
                BluetoothDevice device=btAdapter.getRemoteDevice(address);
                chatService.connect(device);
                
                Intent intent = new Intent(AndroJanken.this ,BluetoothEx.class);
                startActivity(intent);

                
            }
            break;
        //発見有効
        case RQ_ENABLE_BT:
            if (resultCode==Activity.RESULT_OK) {
                chatService=new BluetoothChatService(this,handler);
            } else {
                Toast.makeText(this,"Bluetoothが有効ではありません",
                	Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
    
    //チャットサーバから情報を取得するハンドラ
    private final Handler handler=new Handler() {
        //ハンドルメッセージ
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            //状態変更
            case MSG_STATE_CHANGE:
                switch (msg.arg1) {
                case BluetoothChatService.STATE_CONNECTED:
                    addText("接続完了");break;
                case BluetoothChatService.STATE_CONNECTING:
                    addText("接続中");break;
                case BluetoothChatService.STATE_LISTEN:
                case BluetoothChatService.STATE_NONE:
                    addText("未接続");break;
                }
                break;
            //メッセージ受信
            case MSG_READ:
                byte[] readBuf=(byte[])msg.obj;
                addText(new String(readBuf,0,msg.arg1));
                break;
            }
        }
    };
    
    //受信テキストの追加
    private void addText(final String text) {
        //ハンドラによるユーザーインタフェース操作
        handler.post(new Runnable(){
            public void run() {
                lblReceive.setText(text+
                    System.getProperty("line.separator")+
                    lblReceive.getText());
                
                
            }
        });
    }
    

}