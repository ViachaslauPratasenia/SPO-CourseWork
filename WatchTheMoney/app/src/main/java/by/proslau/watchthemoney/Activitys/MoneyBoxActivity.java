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
import by.proslau.watchthemoney.database.DBHelper;
import by.proslau.watchthemoney.database.DBMoneyBoxHelper;

/**
 * Created by user on 23.04.2018.
 */

public class MoneyBoxActivity extends Activity implements View.OnClickListener{
    private static final int CM_DELETE_ID = 1;
    Button btnAdd;
    ListView lvMoneyBox;
    DBMoneyBoxHelper dbMoneyBoxHelper;
    SimpleCursorAdapter simpleCursorAdapter;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.money_box_activity);

        dbMoneyBoxHelper = new DBMoneyBoxHelper(this);
        dbMoneyBoxHelper.open();

        cursor = dbMoneyBoxHelper.getAllData();
        cursor.requery();


        String[] from = new String[]{DBHelper.COLUMN_MONEYBOX_MONEY, DBHelper.COLUMN_MONEYBOX_DATE, DBHelper.COLUMN_MONEYBOX_NOTE};
        int[] to = new int[] {R.id.tv_money_box_money, R.id.tv_money_box_date, R.id.tv_money_box_note};

        simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.money_box_item, cursor, from, to);
        lvMoneyBox = (ListView) findViewById(R.id.lv_money_box);
        lvMoneyBox.setAdapter(simpleCursorAdapter);
        registerForContextMenu(lvMoneyBox);
        btnAdd = (Button) findViewById(R.id.money_box_add);
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.money_box_add:
                Intent intent = new Intent(this, InputMoneyBoxActivity.class);
                startActivityForResult(intent, 1);
                break;
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
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            dbMoneyBoxHelper.delRec(acmi.id);
            cursor.requery();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(data == null) return;
        double money = data.getDoubleExtra("money", 0);
        String date = data.getStringExtra("date");
        String note = data.getStringExtra("note");
        dbMoneyBoxHelper.addRec(money, date, note);
        cursor.requery();
    }

    protected void onDestroy(){
        super.onDestroy();
        dbMoneyBoxHelper.close();
    }
}
