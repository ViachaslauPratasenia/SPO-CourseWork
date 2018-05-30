package by.proslau.watchthemoney.Activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.Toast;

import by.proslau.watchthemoney.R;
import by.proslau.watchthemoney.database.DBCategoryHelper;
import by.proslau.watchthemoney.database.DBHelper;

import static by.proslau.watchthemoney.ConstainsPreference.*;

/**
 * Created by user on 19.05.2018.
 */

public class CategoryActivity extends Activity implements View.OnClickListener{
    private static final int CM_DELETE_ID = 1;
    private ExpandableListView listView;
    private DBCategoryHelper db;
    private Cursor cursor;
    private  Cursor cursorPref;
    private Button btnAdd;

    private SharedPreferences sharedPreferences;
    private double currentBalance;
    private double spentBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_activity);

        db = new DBCategoryHelper(this);
        db.open();

        cursor = db.getCategoryData();
        startManagingCursor(cursor);

        String[] groupFrom = {DBHelper.CATEGORY_COLUMN_NAME};
        int[] groupTo = {android.R.id.text1};

        String[] childFrom = {DBHelper.COSTS_COLUMN_MONEY, DBHelper.COSTS_COLUMN_DATE,
                    DBHelper.COSTS_COLUMN_NOTE};
        int[] childTo = {R.id.tv_category_child_money, R.id.tv_category_child_date,
                    R.id.tv_category_child_note};

        SimpleCursorTreeAdapter simpleCursorTreeAdapter = new MyAdapter(this, cursor,
                android.R.layout.simple_expandable_list_item_1, groupFrom, groupTo,
                R.layout.category_child_item, childFrom, childTo);

        listView = (ExpandableListView) findViewById(R.id.elv_category);
        listView.setAdapter(simpleCursorTreeAdapter);
        registerForContextMenu(listView);
        btnAdd = (Button) findViewById(R.id.category_add);
        btnAdd.setOnClickListener(this);
    }

    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.category_add:
                Intent intent = new Intent(this, InputCostActivity.class);
                startActivityForResult(intent,1);
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
        try {
            if(item.getItemId() == CM_DELETE_ID){
                ExpandableListView.ExpandableListContextMenuInfo elcmi =
                        (ExpandableListView.ExpandableListContextMenuInfo) item.getMenuInfo();

                cursorPref = db.getMoney(elcmi.id);
                cursorPref.moveToFirst();
                double num = cursorPref.getDouble(0);
                setPreference(num, 0);
                db.delRec(elcmi.id);
                cursor.requery();
                return true;
            }
        } catch (Exception e) {
            Toast.makeText(this, "Нельзя удалить из названия категории",
                    Toast.LENGTH_SHORT).show();
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(data == null) return;
        double money = data.getDoubleExtra("money", 0);
        String date = data.getStringExtra("date");
        String note = data.getStringExtra("note");
        int category = data.getIntExtra("category", 0);
        setPreference(money, 1);
        db.addRec(money, date, note, category);
        cursor.requery();
    }

    public void setPreference(double money, int check){
        sharedPreferences = getSharedPreferences(APP_PREFERENCE, MODE_PRIVATE);
        String curr = sharedPreferences.getString(CURRENT_BUDGET, "");
        String spent = sharedPreferences.getString(SPENT_BUDGET, "");
        try{
            if(check == 1){
                currentBalance = Double.parseDouble(curr);
                spentBalance = Double.parseDouble(spent);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                spentBalance += money;
                currentBalance -= money;
                editor.putString(CURRENT_BUDGET, currentBalance + "");
                editor.putString(SPENT_BUDGET, spentBalance + "");
                editor.commit();
            }
            else if(check == 0){
                currentBalance = Double.parseDouble(curr);
                spentBalance = Double.parseDouble(spent);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                spentBalance -= money;
                currentBalance += money;
                editor.putString(CURRENT_BUDGET, currentBalance + "");
                editor.putString(SPENT_BUDGET, spentBalance + "");
                editor.commit();
            }
        }catch (NumberFormatException e){
            Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();
        }
    }

    class MyAdapter extends SimpleCursorTreeAdapter{

        public MyAdapter(Context context, Cursor cursor, int groupLayout,
                         String[] groupFrom, int[] groupTo, int childLayout,
                         String[] childFrom, int[] childTo){
            super(context, cursor, groupLayout, groupFrom, groupTo,
                    childLayout, childFrom, childTo);
        }

        @Override
        protected Cursor getChildrenCursor(Cursor cursor) {
            int idColumn = cursor.getColumnIndex(DBHelper.CATEGORY_COLUMN_ID);
            return  db.getCosts(cursor.getInt(idColumn));
        }
    }

}
