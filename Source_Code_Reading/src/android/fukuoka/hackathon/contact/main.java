package android.fukuoka.hackathon.contact;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.Contacts.ContactMethods;
import android.provider.Contacts.ContactMethodsColumns;
import android.provider.Contacts.People;
import android.provider.Contacts.Phones;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.PhoneLookup;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


@SuppressWarnings({ "deprecation", "unused" })
public class main extends Activity implements OnClickListener{

	private static final int REQUEST_PICK_CONTACT = 1;
	
	private Button btn1;
	private Button btn2;
	private TextView Name_v;
	private TextView Add_v;
	private TextView Phone_v;
	private LinearLayout Layout;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		btn1 =(Button) findViewById(R.id.PutCont);
		btn1.setOnClickListener(this);
		btn2 = (Button) findViewById(R.id.GetCont);
		btn2.setOnClickListener(this);
		Layout= (LinearLayout) findViewById(R.id.layout);
		
		Name_v = new TextView(this);
		Add_v = new TextView(this);
		Phone_v = new TextView(this);
		Layout.addView(Name_v);
		Layout.addView(Add_v);
		Layout.addView(Phone_v);
		
	}
	
	@Override
	public void onClick(View v) {
		if( v.getId() == R.id.GetCont) {
			/* Data取得*/
			GetCont();
		}else if(v.getId() == R.id.PutCont){
			/* Data登録*/
			SetCont();
		}
	}
	
	private void GetCont(){
		//Contacts よりデータ取得
		//People.CONTENT_URI
		Intent intent = new Intent(Intent.ACTION_PICK, People.CONTENT_URI);
		startActivityForResult(intent, REQUEST_PICK_CONTACT);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent returnedIntent) {
		//Contactsより戻ってきたデータ
		if (requestCode == REQUEST_PICK_CONTACT && resultCode == Activity.RESULT_OK) {
			Uri uri = returnedIntent.getData();
			Cursor personCursor = managedQuery(uri, null, null, null, null);
			if (personCursor.moveToFirst()) {
				//People._ID
				int idIndex = personCursor.getColumnIndexOrThrow(People._ID);
				//People.Name
				//PhoneLookup.DISPLAY_NAME
				int nameIndex = personCursor.getColumnIndexOrThrow(People.NAME);
				
				String id = personCursor.getString(idIndex);
				String name = personCursor.getString(nameIndex);

				//People.Number
				StringBuilder where = new StringBuilder();
				where.append(Phones.PERSON_ID).append(" == ?");
				String selection = where.toString();
				String[] selectionArgs = {id};
				Cursor PhoneCursor = managedQuery(Phones.CONTENT_URI, null, selection, selectionArgs, null);
				String number ="";
				if (PhoneCursor.moveToFirst()) {
					int numberIndex = PhoneCursor.getColumnIndexOrThrow(People.NUMBER);
					number = PhoneCursor.getString(numberIndex);
				}
				PhoneCursor.close();
				
				String address = "";
				StringBuilder where1 = new StringBuilder();
				where1.append(ContactMethods.PERSON_ID).append(" == ? AND ");
				where1.append(ContactMethods.KIND).append(" == ?");
				selection = where1.toString();
				String[] selectionArgs1 = {id, String.valueOf(Contacts.KIND_POSTAL)};
				Cursor addressCursor = managedQuery(ContactMethods.CONTENT_URI, null, selection, selectionArgs1, null);
				if (addressCursor.moveToFirst()) {
					int addressIndex = addressCursor.getColumnIndexOrThrow(ContactMethodsColumns.DATA);
					address = addressCursor.getString(addressIndex);
				}
				addressCursor.close();

				Name_v.setText(name);
				Phone_v.setText(number);
				Add_v.setText(address);
				
			}
		}
	}

	private void SetCont(){
		//Intent 経由で追加処理
		Intent i = new Intent(Intent.ACTION_INSERT_OR_EDIT);
			i.putExtra(ContactsContract.Intents.Insert.NAME,"福岡　太郎");
			i.putExtra(ContactsContract.Intents.Insert.EMAIL,"android-group-japan-fukuoka@googlegroups.com");
			i.putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE,ContactsContract.CommonDataKinds.Email.TYPE_WORK);
			i.putExtra(ContactsContract.Intents.Insert.PHONE,"092-731-0000");
			i.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE,ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
			i.putExtra(ContactsContract.Intents.Insert.POSTAL,"福岡市中央区XX ６－X－Y");
			i.putExtra(ContactsContract.Intents.Insert.POSTAL_TYPE,ContactsContract.CommonDataKinds.StructuredPostal.TYPE_WORK);
			i.setType(ContactsContract.Contacts.CONTENT_ITEM_TYPE);
			startActivity(i);
	}

}
