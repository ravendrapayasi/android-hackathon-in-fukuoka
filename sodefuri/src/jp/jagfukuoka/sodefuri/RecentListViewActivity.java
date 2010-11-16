package jp.jagfukuoka.sodefuri;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RecentListViewActivity extends ListActivity {
	boolean debug = true;
	public static final String SCREEN_NAME = "SCREEN_NAME";

	String[] projection = new String[] {
			RecentContentProvider.MAC_ADDRESS,
         };
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  
	  //test data insert
	  ContentValues values = new ContentValues();
	  values.put(RecentContentProvider.MAC_ADDRESS, "ab:cd:ef:ge");
	  Uri uri = getContentResolver().insert(RecentContentProvider.CONTENT_URI, values);
	  //test data get
	  Cursor managedCursor = managedQuery(RecentContentProvider.CONTENT_URI, projection, null, null, null);
	  List<String> list = getColumnData(managedCursor);
	  //DB éÊìæèàóù
	  setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, list));

	  ListView lv = getListView();
	  lv.setTextFilterEnabled(true);

	  lv.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view,
	        int position, long id) {
	      // When clicked, show a toast with the TextView text
	      Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
	          Toast.LENGTH_SHORT).show();
	    }
	  });
	}

	private List<String> getColumnData(Cursor cur){ 
		List<String> list = new ArrayList<String>();
	    if (cur.moveToFirst()) {
	        int int_mac_address = cur.getColumnIndex(RecentContentProvider.MAC_ADDRESS);
	        do {
	            // Get the field values
	        	String mac_address = cur.getString(int_mac_address);
//	            map.put(MAC_ADDRESS, mac_address);
//	            map.put(SCREEN_NAME, screen_name);
	            list.add(mac_address);
	        } while (cur.moveToNext());
	    }
	    return list;
	}
}
