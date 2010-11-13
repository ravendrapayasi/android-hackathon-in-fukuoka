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

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
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
        
        //-----[繝懊ち繝ｳ縺ｮ險ｭ螳咯
        Button button1 = (Button)findViewById(R.id.Button01);
        button1.setOnClickListener(this);
        
        
        
        
        
        
      //-----[Bluetooth縺ｮMAC繧｢繝峨Ξ繧ｹ蜿門ｾ余
        BluetoothAdapter bt = BluetoothAdapter.getDefaultAdapter();
		if(bt == null)
		{
			Toast.makeText(this, "Bluetooth縺ｯ繧ｵ繝昴�繝医＆繧後※縺�∪縺帙ｓ", Toast.LENGTH_LONG).show();
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
    //   繧ｯ繝ｪ繝�け繧､繝吶Φ繝�    //------------------------------
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
    	//-----[繧ｯ繝ｩ繧､繧｢繝ｳ繝郁ｨｭ螳咯
        HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		
		//-----[JSON縺ｮ菴懈�]
		EditText et = (EditText) findViewById(R.id.EditText01);
		String json = "{\"screen_name\":\""+et.getText()+"\", \"mac_address\":\""+getMacAddress()+"\"}";
		
		//-----[POST騾∽ｿ｡縺吶ｋ繝��繧ｿ繧呈�邏江
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
		nameValuePair.add(new BasicNameValuePair("json", json));
		
		try
		{
			//-----[POST騾∽ｿ｡]
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));
			HttpResponse response = httpclient.execute(httppost);
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			response.getEntity().writeTo(byteArrayOutputStream);
			
			//-----[繧ｵ繝ｼ繝舌�縺九ｉ縺ｮ蠢懃ｭ斐ｒ蜿門ｾ余
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