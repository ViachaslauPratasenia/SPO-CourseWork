package by.proslau.watchthemoney.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import by.proslau.watchthemoney.R;
import by.proslau.watchthemoney.dialog.BudgetDialog;
import by.proslau.watchthemoney.dialog.DialogCategory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    SharedPreferences sPref;
    private static final String BUDGET = "budget";
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button addMainButton = (Button) findViewById(R.id.add_main_button);
        Button deleteMainButton = (Button) findViewById(R.id.delete_main_button);

        registerForContextMenu(addMainButton);
        registerForContextMenu(deleteMainButton);

        textView = (TextView) findViewById(R.id.tv_main_start_balance_num);
        loadText();

        Button btnUpload = (Button) findViewById(R.id.main_start_balance_upload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadText();
                Toast.makeText(getBaseContext(), "Upload", Toast.LENGTH_SHORT).show();
            }
        });

        /*ListView listView = (ListView) findViewById(R.id.main_list_view);
        final String[] catNames = new String[] {
                "Рыжик", "Барсик", "Мурзик", "Мурка", "Васька",
                "Томасина", "Кристина", "Пушок", "Дымка", "Кузя",
                "Китти", "Масяня", "Симба"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, catNames);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                Toast.makeText(getApplicationContext(), ((TextView) itemClicked).getText(),
                        Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    void loadText() {
        sPref = getPreferences(MODE_PRIVATE);
        Float savedText = sPref.getFloat(BUDGET, 0);
        String a = savedText.toString();
        textView.setText(a);
        Toast.makeText(this, "Text loaded", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo contextMenuInfo){
        switch (view.getId()){
            case R.id.add_main_button:
                menu.add(0, 1,0,"Добавить категорию");
                menu.add(0,2,0,"Добавить бюджет");
                break;
            case R.id.delete_main_button:
                menu.add(0,3,0,"Удалить категорию");
                menu.add(0,4,0,"Удалить из бюджета");
                break;
            case R.id.arrears:
                menu.add(0,5,0, "Вам должны");
                menu.add(0,6,0,"Вы должны");
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){
            case 1:
                DialogCategory dialog = new DialogCategory();
                dialog.show(getFragmentManager(), "first");
                break;
            case 2:
                BudgetDialog budgetDialog = new BudgetDialog();
                budgetDialog.show(getFragmentManager(), "budget");
                break;
            case 3:
                DialogCategory dialogDelete = new DialogCategory();
                dialogDelete.show(getFragmentManager(), "second");
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent;
        int id = item.getItemId();

        if (id == R.id.arrears) {
            intent = new Intent(this, ArrearsActivity.class);
            startActivity(intent);
        } else if (id == R.id.birthdays) {
            intent = new Intent(this, BirthdaysActivity.class);
            startActivity(intent);
        } else if (id == R.id.money_box) {
            intent = new Intent(this, MoneyBoxActivity.class);
            startActivity(intent);
        } else if (id == R.id.category){
            intent = new Intent(this, CategoryActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
