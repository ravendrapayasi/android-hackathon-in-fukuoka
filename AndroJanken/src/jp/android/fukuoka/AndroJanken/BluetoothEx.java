package jp.android.fukuoka.AndroJanken;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BluetoothEx extends Activity
    implements OnClickListener {
    //メッセージ定数
    public static final int MSG_STATE_CHANGE=1;
    public static final int MSG_READ        =2;

    //リクエスト定数
    private static final int RQ_CONNECT_DEVICE=1;
    private static final int RQ_ENABLE_BT     =2;

    //Bluetooth
    private BluetoothAdapter     btAdapter;
    private BluetoothChatService chatService;

    //UI
    //private TextView lblReceive;//受信ラベル
    private Button tyoki,gu,pa;
    private String message = null;

    //private Context context;
    //private EditText edtSend;   //送信エディットテキスト
    // @private Button   btnSend;   //送信ボタン

    //アプリ生成時に呼ばれる
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.select);
        tyoki  = (Button) findViewById(R.id.Button01);
        tyoki.setOnClickListener(this);

        gu  = (Button) findViewById(R.id.Button02);
        gu.setOnClickListener(this);

        pa  = (Button) findViewById(R.id.Button03);
        pa.setOnClickListener(this);
        /*
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //レイアウトの生成
        LinearLayout layout=new LinearLayout(this);
        layout.setBackgroundColor(Color.rgb(255,255,255));
        layout.setOrientation(LinearLayout.VERTICAL);
        setContentView(layout);

        //送信エディットテキストの生成
        edtSend=new EditText(this);
        edtSend.setId(2);
        edtSend.setText("",TextView.BufferType.NORMAL);
        setLLParams(edtSend,
            LinearLayout.LayoutParams.FILL_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.addView(edtSend);

        //送信ボタンの生成
        btnSend=new Button(this);
        btnSend.setText("送信");
        btnSend.setOnClickListener(this);
        setLLParams(btnSend);
        layout.addView(btnSend);

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
        */
        //Bluetoothアダプタ

        btAdapter=BluetoothAdapter.getDefaultAdapter();
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

    //アプリ破棄時に呼ばれる
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (chatService!=null) chatService.stop();
    }

    //他のBluetooth端末からの発見を有効化(4)
    private void ensureDiscoverable() {
        if (btAdapter.getScanMode()!=
            BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent=new Intent(
                BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(
                BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,300);
            startActivity(discoverableIntent);
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
                    Toast.makeText(BluetoothEx.this, "接続完了", Toast.LENGTH_LONG).show();break;
                case BluetoothChatService.STATE_CONNECTING:
                	Toast.makeText(BluetoothEx.this, "接続中", Toast.LENGTH_LONG).show();;break;
                case BluetoothChatService.STATE_LISTEN:
                case BluetoothChatService.STATE_NONE:
                	Toast.makeText(BluetoothEx.this, "未接続", Toast.LENGTH_LONG).show();break;
                }
                break;
            //メッセージ受信
            case MSG_READ:
                byte[] readBuf=(byte[])msg.obj;
                Toast.makeText(BluetoothEx.this,new String(readBuf,0,msg.arg1), Toast.LENGTH_LONG).show();
                break;
            }
        }
    };

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

    //オプションメニュー生成時に呼ばれる
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuItem item0=menu.add(0,0,0,"端末検索");
        item0.setIcon(android.R.drawable.ic_search_category_default);

        MenuItem item1=menu.add(0,1,0,"発見有効");
        item1.setIcon(android.R.drawable.ic_menu_call);

        return true;
    }

    //オプションメニュー選択時に呼ばれる
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        //検索
        case 0:
            Intent serverIntent=new Intent(this,DeviceListActivity.class);
            startActivityForResult(serverIntent,RQ_CONNECT_DEVICE);
            return true;

        //発見有効
        case 1:
            ensureDiscoverable();
            return true;

        }
        return false;
    }

    /*
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

    */
    //ボタンクリックイベントの処理
    public void onClick(View v) {
        if (v==gu) {
        	Log.e("BluetoothEx.class","gu-");
            try {
                //メッセージの送信
                message="0";
                if (message.length()>0) {
                    chatService.write(message.getBytes());
                }
                Toast.makeText(this, "チョキ", Toast.LENGTH_SHORT).show();
                //edtSend.setText("",TextView.BufferType.NORMAL);
            } catch (Exception e) {
                Toast.makeText(this, "通信失敗しました", Toast.LENGTH_SHORT).show();
            }
        }

        if (v==tyoki) {
        	Log.e("BluetoothEx.class","gu-");
            try {
                //メッセージの送信
                message="1";
                if (message.length()>0) {
                    chatService.write(message.getBytes());
                }
                Toast.makeText(this, "グー", Toast.LENGTH_SHORT).show();
                //edtSend.setText("",TextView.BufferType.NORMAL);
            } catch (Exception e) {
                Toast.makeText(this, "通信失敗しました", Toast.LENGTH_SHORT).show();
            }
        }
        if (v==pa) {
        	Log.e("BluetoothEx.class","pa-");
            try {
                //メッセージの送信
                message="2";
                if (message.length()>0) {
                    chatService.write(message.getBytes());
                }
                Toast.makeText(this, "パー", Toast.LENGTH_SHORT).show();
                //edtSend.setText("",TextView.BufferType.NORMAL);
            } catch (Exception e) {
                Toast.makeText(this, "通信失敗しました", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*
    //ライナーレイアウトのパラメータ指定
    private static void setLLParams(View view) {
        view.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    //ライナーレイアウトのパラメータ指定
    private static void setLLParams(View view,int w,int h) {
        view.setLayoutParams(new LinearLayout.LayoutParams(w,h));
    }
    */
}
