package org.contactEx;

import static android.provider.ContactsContract.*;
import static android.provider.ContactsContract.CommonDataKinds.*;
import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public final class ContactEx extends Activity {
	private ListView lvContacts;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		findViews();		
		createContactList();
	}

	private void createContactList() {
		Cursor cursor = getContacts();
		String[] fields = { Contacts._ID, Contacts.DISPLAY_NAME };
		int[] tvResID = {R.id.contactID, R.id.contactName};

		SimpleCursorAdapter adapter = new SimpleCursorAdapter(
				this, R.layout.contactlist, cursor, 
				fields, tvResID );
		lvContacts.setAdapter(adapter);
	}

	private Cursor getContacts() {
		Uri uri = Contacts.CONTENT_URI;
		String[] fields = { Contacts._ID, Contacts.DISPLAY_NAME };
		String selection = null;
		String[] selectionArgs = null;
		String sortOrder = Contacts.DISPLAY_NAME;
		return getContentResolver().query(
				uri, fields, selection, selectionArgs, sortOrder);
	}

	private Cursor getPhones(long id) {
		Uri uri = Phone.CONTENT_URI;
		String[] fields = { Phone.TYPE, Phone.NUMBER };
		String selection = Phone.CONTACT_ID + " = "+ id;
		String[] selectionArgs = null;
		String sortOrder = null;
		return getContentResolver().query(
				uri, fields, selection, selectionArgs, sortOrder);
	}	
	
	private void findViews() {
		lvContacts = (ListView) findViewById(R.id.contactList);
		lvContacts.setOnItemClickListener(new OnItemClickListener() {
			@Override //點選ListView上面的項目
			public void onItemClick(AdapterView<?> parent, 
					View view, int position, long id) {
				LinearLayout linear = (LinearLayout)view;
				CharSequence contactName = 
					((TextView)linear.getChildAt(1)).getText();
				StringBuilder text = new StringBuilder();
				Cursor phones = getPhones(id);
				text.append(contactName);
				
				if(phones.getCount()<=0){
					text.append("\n" + getString(R.string.phoneNo_not_found));
					Toast.makeText(ContactEx.this, text, Toast.LENGTH_LONG).show();
					return;
				}
				
				while(phones.moveToNext()){
					int typeID = 
						phones.getInt(phones.getColumnIndex(Phone.TYPE));
					String type = 
						getString(Phone.getTypeLabelResource(typeID));
					String phoneNo = 
						phones.getString(phones.getColumnIndex(Phone.NUMBER));
					text.append("\n" + type + "：" + phoneNo);
				}
				Toast.makeText(ContactEx.this, text, Toast.LENGTH_LONG).show();				
			}
		});		
	}
}
