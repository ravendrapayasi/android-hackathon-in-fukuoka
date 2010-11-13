package jp.jagfukuoka;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import backport.android.bluetooth.BluetoothAdapter;
import backport.android.bluetooth.BluetoothDevice;

import jp.jagfukuoka.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener  {
	
	private static final String url = "http://sodefuri.appspot.com/register";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //-----[ボタンの設定]
        Button button1 = (Button)findViewById(R.id.Button01);
        button1.setOnClickListener(this);
        
        
        
        
        
        
      //-----[BluetoothのMACアドレス取得]
        BluetoothAdapter bt = BluetoothAdapter.getDefaultAdapter();
		if(bt == null)
		{
			Toast.makeText(this, "Bluetoothはサポートされていません", Toast.LENGTH_LONG).show();
		}
		else
		{
			/*Set<BluetoothDevice> pairedDevices = bt.getBondedDevices();
			// If there are paired devices
			if (pairedDevices.size() > 0) {
			    // Loop through paired devices
			    for (BluetoothDevice device : pairedDevices) {
			        // Add the name and address to an array adapter to show in a ListView
			        String str = device.getName() + "\n" + device.getAddress();
			    }
			}*/
			
			TextView tv = (TextView) findViewById(R.id.text);
			tv.setText(bt.toString());
			
		}
    }
    
    //------------------------------
    //   クリックイベント
    //------------------------------
    public void onClick(View v) {
    	switch(v.getId())
    	{
    		case R.id.Button01:
    			sendJson();
    			break;
    	}
    }
    
    private void sendJson()
    {
    	//-----[クライアント設定]
        HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		
		//-----[JSONの作成]
		EditText et = (EditText) findViewById(R.id.EditText01);
		String json = "{\"screen_name\":\""+et.getText()+"\", \"mac_address\":\""+getMacAddress()+"\"}";
		
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
				Toast.makeText(this, byteArrayOutputStream.toString(), Toast.LENGTH_LONG).show();
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

	private String getMacAddress()
	{
		
		
		String address = "hoge";
		return address;
	}
}