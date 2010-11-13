package jp.jagfukuoka;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import jp.jagfukuoka.R;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener  {
	
	private static final String url = "http://sodefuri.appspot.com/register";
	
	private static final int REQUEST_ENABLE_BT = 1;
	private static final int REQUEST_STATE_CHANGE_BT = 2;
	private TextView tv_result = null;
	private BluetoothAdapter mBluetoothAdapter = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        tv_result = (TextView) findViewById(R.id.tv_result);
        
        //-----[ボタンの設定]
        Button button1 = (Button)findViewById(R.id.Button01);
        button1.setOnClickListener(this);
    }
    
    //------------------------------
    //   クリックイベント
    //------------------------------
    public void onClick(View v) {
    	switch(v.getId())
    	{
    		case R.id.Button01:
    			checkBluetooth();
    			break;
    	}
    }
    
    private void checkBluetooth()
	{
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
		if (mBluetoothAdapter == null)
		{
			//Bluetoothはサポートされてません
		}
		else
		{
			if (mBluetoothAdapter.isEnabled())
			{
				//-----[利用可能なので次の処理へ]
				 getLocalInformation();
			}
			else
			{
				//-----[利用不可なので許可アラート表示]
				 Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				 startActivityForResult(enableBTIntent, REQUEST_ENABLE_BT);
				 Intent stateChangedBTIntent = new Intent(BluetoothAdapter.ACTION_STATE_CHANGED);
				 startActivityForResult(stateChangedBTIntent, REQUEST_STATE_CHANGE_BT);
			}
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == REQUEST_ENABLE_BT)
		{
			if (resultCode == RESULT_OK)
			{
				 //Bluetoothが利用可能になりました
				 getLocalInformation();
			}
			else if (resultCode == RESULT_CANCELED)
			{
				//Bluetoothは利用不可です
			}
		}
		else if (requestCode == REQUEST_STATE_CHANGE_BT)
		{
			switch (resultCode)
			{
				case BluetoothAdapter.STATE_TURNING_ON:
					//STATE_TURNING_ON
					
					break;
				case BluetoothAdapter.STATE_ON:
					 //STATE_ON
					
					 break;
				case BluetoothAdapter.STATE_TURNING_OFF:
					 //STATE_TURNING_OFF
					
					 break;
				case BluetoothAdapter.STATE_OFF:
					 //STATE_OFF
					
					 break;
			}
		}
	}
	
	private void getLocalInformation()
	{
		switch (mBluetoothAdapter.getScanMode())
		{
			case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
				
				break;
			case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
				
				break;
			case BluetoothAdapter.SCAN_MODE_NONE:
				
				break;
		}
		
		switch (mBluetoothAdapter.getState())
		{
			case BluetoothAdapter.STATE_OFF:
				
				break;
			case BluetoothAdapter.STATE_ON:
				
				break;
			case BluetoothAdapter.STATE_TURNING_OFF:
				
				break;
			case BluetoothAdapter.STATE_TURNING_ON:
				
				break;
		}
		
		//mBluetoothAdapter.getName();
		//mBluetoothAdapter.getAddress();
		sendJson(mBluetoothAdapter.getAddress());
	}
    
    
	private void sendJson(String address)
    {
    	//-----[クライアント設定]
        HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		
		//-----[JSONの作成]
		EditText et = (EditText) findViewById(R.id.EditText01);
		String json = "{\"screen_name\":\""+et.getText()+"\", \"mac_address\":\""+address+"\"}";
		
		//-----[POST送信するデータを格納]
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
		nameValuePair.add(new BasicNameValuePair("json", json));
		
		try
		{
			//-----[POST送信]
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));
			HttpResponse response = httpclient.execute(httppost);
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			response.getEntity().writeTo(byteArrayOutputStream);
			
			//-----[サーバーからの応答を取得]
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
			{
				Toast.makeText(this, "登録しました", Toast.LENGTH_LONG).show();
			}
			else
			{
				Toast.makeText(this, "[error]: "+response.getStatusLine(), Toast.LENGTH_LONG).show();
			}
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
    }
}