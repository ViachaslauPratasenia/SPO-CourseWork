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
import by.proslau.watchthemoney.database.DBDebtorHelper;
import by.proslau.watchthemoney.database.DBHelper;

/**
 * Created by user on 16.04.2018.
 */

public class ArrearsActivity extends Activity implements View.OnClickListener {

    private static final int CM_DELETE_ID = 1;
    ListView lvData;
    DBDebtorHelper db;
    SimpleCursorAdapter simpleCursorAdapter;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arrears_activity);


        db = new DBDebtorHelper(this);
        db.open();

        cursor = db.getAllData();
        startManagingCursor(cursor);

        String[] from = new String[] {DBHelper.COLUMN_DEPTOR_NAME, DBHelper.COLUMN_DEPTOR_MONEY,
            DBHelper.COLUMN_DEPTOR_CHOISE};
        int[] to = new int[]{R.id.tv_deptor_name, R.id.tv_deptor_money};

        simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.deptor_item, cursor, from, to);
        lvData = (ListView)findViewById(R.id.arrears_list_view);
        lvData.setAdapter(simpleCursorAdapter);
        registerForContextMenu(lvData);

        Button addArrearsButton = (Button) findViewById(R.id.arrears_add);
        addArrearsButton.setOnClickListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo contextMenuInfo){
        super.onCreateContextMenu(menu, view, contextMenuInfo);
        menu.add(0, CM_DELETE_ID, 0, "Удалить запись");
    }
    //ДОБАВИТЬ КОНТАКТЫ
    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getItemId() == CM_DELETE_ID){
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            db.delRec(acmi.id);
            cursor.requery();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.arrears_add){
            Intent intent = new Intent(this, InputDeptorActivity.class);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(data == null) return;
        String name = data.getStringExtra("name");
        double money = data.getDoubleExtra("money",0);
        int check = data.getIntExtra("check", -1);
        db.addRec(name, money, check);
        cursor.requery();
    }

    protected void onDestroy(){
        super.onDestroy();
        db.close();
    }
}
