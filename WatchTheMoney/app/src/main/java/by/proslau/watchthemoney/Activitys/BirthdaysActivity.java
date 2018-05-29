package by.proslau.watchthemoney.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import by.proslau.watchthemoney.R;
import by.proslau.watchthemoney.database.DBBirthdaysHelper;
import by.proslau.watchthemoney.database.DBHelper;

/**
 * Created by user on 18.04.2018.
 */

public class BirthdaysActivity extends Activity implements View.OnClickListener{
    private static final int CM_DELETE_ID = 1;
    ListView lvBirthdays;
    DBBirthdaysHelper dbBirthdaysHelper;
    SimpleCursorAdapter simpleCursorAdapter;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.birthdays_activity);

        dbBirthdaysHelper = new DBBirthdaysHelper(this);
        dbBirthdaysHelper.open();

        cursor = dbBirthdaysHelper.getAllData();
        cursor.requery();

        String from[] = new String[] {DBHelper.COLUMN_BIRTHDAYS_NAME, DBHelper.COLUMN_BIRTHDAYS_DATE};
        int[] to = new int[] {R.id.tv_birthdays_name, R.id.tv_birhdays_date};

        simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.birthdays_item, cursor,
                from, to);
        lvBirthdays = (ListView) findViewById(R.id.lv_birthdays);
        lvBirthdays.setAdapter(simpleCursorAdapter);
        registerForContextMenu(lvBirthdays);

        Button addButton = (Button) findViewById(R.id.birthdays_add);
        addButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.birthdays_add){
            Intent intent = new Intent(this, InputBirthdaysActivity.class);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo contextMenuInfo){
        super.onCreateContextMenu(menu, view, contextMenuInfo);
        menu.add(0, CM_DELETE_ID, 0, "Удалить запись");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getItemId() == CM_DELETE_ID){
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo)
                    item.getMenuInfo();
            dbBirthdaysHelper.delRec(acmi.id);
            cursor.requery();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(data == null) return;
        String name = data.getStringExtra("name");
        String date = data.getStringExtra("date");
        dbBirthdaysHelper.addRec(name, date);
        cursor.requery();
    }

    protected void onDestroy(){
        super.onDestroy();
        dbBirthdaysHelper.close();
    }
}
