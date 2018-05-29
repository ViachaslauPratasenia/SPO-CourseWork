package by.proslau.watchthemoney.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

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
    Cursor cursorPref;

    SharedPreferences sharedPreferences;
    private static final String APP_PREFERENCE = "WTMPreference";
    private static final String YOUR_DEPTS_BUDGET = "your_depts_budget";
    private static final String DEPT_BUDGET = "dept_budget";

    double yourDepts;
    double depts;

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
        int[] to = new int[]{R.id.tv_deptor_name, R.id.tv_deptor_money, R.id.tv_deptor_choise};

        simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.deptor_item, cursor,
                from, to);
        lvData = (ListView)findViewById(R.id.lv_deptors);
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

    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getItemId() == CM_DELETE_ID){
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo)
                    item.getMenuInfo();

            cursorPref = db.getMoneyDeptor(acmi.id);
            cursorPref.moveToFirst();
            double money = cursorPref.getDouble(cursorPref.getColumnIndex(DBHelper.COLUMN_DEPTOR_MONEY));
            String choice = cursorPref.getString(cursorPref.getColumnIndex(DBHelper.COLUMN_DEPTOR_CHOISE));
            deletePreference(money, choice);

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
        String check = data.getStringExtra("choise");
        if(check.equals("Вы должны")){
            setPreference(money, 1);
        }
        else if(check.equals("Вам должны")){
            setPreference(money, 0);
        }
        else Toast.makeText(this, "Возникла ошибка", Toast.LENGTH_SHORT).show();

        db.addRec(name, money, check);
        cursor.requery();
    }

    public void setPreference(double money, int check){
        sharedPreferences = getSharedPreferences(APP_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        try{
            if(check == 1){
                String your = sharedPreferences.getString(YOUR_DEPTS_BUDGET, "");
                yourDepts = Double.parseDouble(your);
                yourDepts += money;
                editor.putString(YOUR_DEPTS_BUDGET, yourDepts + "");
                editor.commit();
            }
            else if(check == 0){
                String deptsStr = sharedPreferences.getString(DEPT_BUDGET, "");
                depts = Double.parseDouble(deptsStr);
                depts += money;
                editor.putString(DEPT_BUDGET, depts + "");
                editor.commit();
            }
        }catch (NumberFormatException e){
            Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();
        }
    }

    public void deletePreference(double money, String choise){
        sharedPreferences = getSharedPreferences(APP_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        try{
            if(choise.equals("Вы должны")){
                String your = sharedPreferences.getString(YOUR_DEPTS_BUDGET, "");
                yourDepts = Double.parseDouble(your);
                yourDepts -= money;
                editor.putString(YOUR_DEPTS_BUDGET, yourDepts + "");
                editor.commit();
            }
            else if(choise.equals("Вам должны")){
                String deptsStr = sharedPreferences.getString(DEPT_BUDGET, "");
                depts = Double.parseDouble(deptsStr);
                depts -= money;
                editor.putString(DEPT_BUDGET, depts + "");
                editor.commit();
            }
        }catch (NumberFormatException e){
            Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        db.close();
    }
}
