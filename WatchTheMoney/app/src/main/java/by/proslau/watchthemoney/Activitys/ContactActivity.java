package by.proslau.watchthemoney.Activitys;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;

import by.proslau.watchthemoney.R;

/**
 * Created by user on 15.05.2018.
 */

public class ContactActivity extends Activity {
    private static final int REQUEST_CODE_READ_CONTACTS = 99;
    private ArrayList<String> stringArrayList = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    private ListView listView;

    private static final Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
    private static final String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
    private static final String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_activity);
        listView = (ListView) findViewById(R.id.lv_contacts);
        int permissionStatus = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS);
        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            stringArrayList = getContacts();
        } else {
            ActivityCompat.requestPermissions(this, new String[]
                { Manifest.permission.READ_CONTACTS }, REQUEST_CODE_READ_CONTACTS);
        }

        arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                stringArrayList);
        arrayAdapter.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                TextView textView = (TextView) view;
                String strText = textView.getText().toString();
                intent.putExtra("contact", strText);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_READ_CONTACTS:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContacts();
                    finish();
                } else {
                    Toast.makeText(this, "Ошибка получения контактов",
                            Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    public ArrayList<String> getContacts(){
        ArrayList<String> arrayList = new ArrayList<>();
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(CONTENT_URI, null,null,
                null,null);
        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));
                if(hasPhoneNumber > 0){
                    arrayList.add(name);
                }
            }
        }
        return arrayList;
    }
}
