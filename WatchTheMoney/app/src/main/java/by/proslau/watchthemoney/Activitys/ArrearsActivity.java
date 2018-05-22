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
import android.widget.Spinner;
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

    SharedPreferences sharedPreferences;
    private static final String APP_PREFERENCE = "WTMPreference";
    private static final String CURRENT_BUDGET = "current_budget";
    private static final String SPENT_BUDGET = "spent_budget";

    double currentBalance;
    double spentBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arrears_activity);



        /*
        cursor = db.getData(0);


        //НЕ РАБОТАЕТ ВЫВОД ТАБЛИЦЫ В СПИННЕРЕ

        spinner = (Spinner) findViewById(R.id.arrears_spinner_choise);
        //ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.arrears_list, R.layout.support_simple_spinner_dropdown_item);
        //adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        //spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                long choise = spinner.getSelectedItemId();
                cursor = db.getData(choise);
                cursor.requery();
                Toast.makeText(getApplicationContext(), "num " + choise, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        db = new DBDebtorHelper(this);
        db.open();

        cursor = db.getAllData();
        startManagingCursor(cursor);

        String[] from = new String[] {DBHelper.COLUMN_DEPTOR_NAME, DBHelper.COLUMN_DEPTOR_MONEY,
            DBHelper.COLUMN_DEPTOR_CHOISE};
        int[] to = new int[]{R.id.tv_deptor_name, R.id.tv_deptor_money, R.id.tv_deptor_choise};

        simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.deptor_item, cursor, from, to);
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
        String check = data.getStringExtra("choise");
        //setPreference(money);
        db.addRec(name, money, check);
        cursor.requery();
    }

    /*public void setPreference(double money){
        sharedPreferences = getSharedPreferences(APP_PREFERENCE, MODE_PRIVATE);
        String curr = sharedPreferences.getString(CURRENT_BUDGET, "");
        String spent = sharedPreferences.getString(SPENT_BUDGET, "");
        try{
            currentBalance = Double.parseDouble(curr);
            spentBalance = Double.parseDouble(spent);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            spentBalance += money;
            currentBalance -= money;
            editor.putString(CURRENT_BUDGET, currentBalance + "");
            editor.putString(SPENT_BUDGET, spentBalance + "");
            editor.commit();
        }catch (NumberFormatException e){
            Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();
        }
    }*/

    protected void onDestroy(){
        super.onDestroy();
        db.close();
    }
}
