package by.proslau.watchthemoney.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
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
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{

    SharedPreferences sPref;

    private static final String BUDGET = "start_budget";
    private static final String CURRENT_BUDGET = "current_budget";
    private static final String SPENT_BUDGET = "spent_budget";

    TextView textViewStartBalance;
    TextView textViewSpentBalance;
    TextView textViewCurrentBalance;

    Button btnChangeStartBalance;
    Button btnStartBalanceUpload;
    Button btnSpentBalanceUpload;
    Button btnCurrentBalanceUpload;

    SwipeRefreshLayout swipeRefreshLayout;

    double startBalance;
    double currentBalance;
    double spentBalance;

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

        textViewStartBalance = (TextView) findViewById(R.id.tv_main_start_balance_num);
        textViewSpentBalance = (TextView) findViewById(R.id.tv_main_spent_balance_num);
        textViewCurrentBalance = (TextView) findViewById(R.id.tv_main_current_balance_num);


        btnStartBalanceUpload = (Button) findViewById(R.id.main_start_balance_upload);
        btnStartBalanceUpload.setOnClickListener(this);

        btnSpentBalanceUpload = (Button) findViewById(R.id.main_spent_balance_upload);
        btnSpentBalanceUpload.setOnClickListener(this);

        btnCurrentBalanceUpload = (Button) findViewById(R.id.main_current_balance_upload);
        btnCurrentBalanceUpload.setOnClickListener(this);

        btnChangeStartBalance = (Button) findViewById(R.id.main_change_start_balance);
        btnChangeStartBalance.setOnClickListener(this);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN);


        loadStartBalanceNum();
        loadCurrentBalanceNum();
        loadSpentBalanceNum();

    }

    void loadStartBalanceNum() {
        sPref = getPreferences(MODE_PRIVATE);
        String a = sPref.getString(BUDGET, "");
        try {
            startBalance = Double.parseDouble(a);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Баланс еще не добавлен", Toast.LENGTH_SHORT).show();
        }
        textViewStartBalance.setText(startBalance + " $");
    }

    void loadCurrentBalanceNum(){
        sPref = getPreferences(MODE_PRIVATE);
        String a = sPref.getString(CURRENT_BUDGET, "");
        try{
            currentBalance = Double.parseDouble(a);
        }catch (NumberFormatException e){
            Toast.makeText(this, "Баланс еще не добавлен", Toast.LENGTH_SHORT).show();
        }
        textViewCurrentBalance.setText(currentBalance + " $");
    }

    void loadSpentBalanceNum(){
        sPref = getPreferences(MODE_PRIVATE);
        String a = sPref.getString(SPENT_BUDGET, "");
        try{
            spentBalance = Double.parseDouble(a);
        }catch (NumberFormatException e){
            Toast.makeText(this, "Баланс еще не добавлен", Toast.LENGTH_SHORT).show();
        }
        textViewSpentBalance.setText(spentBalance + " $");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_start_balance_upload:
                loadStartBalanceNum();
                break;
            case R.id.main_spent_balance_upload:
                loadSpentBalanceNum();
                break;
            case R.id.main_current_balance_upload:
                loadCurrentBalanceNum();
                break;

            case R.id.main_change_start_balance:
                BudgetDialog budgetDialog = new BudgetDialog();
                budgetDialog.show(getFragmentManager(), "budget");
                break;
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Отменяем анимацию обновления
                swipeRefreshLayout.setRefreshing(false);
                loadStartBalanceNum();
                loadSpentBalanceNum();
                loadCurrentBalanceNum();
            }
        }, 500);
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
