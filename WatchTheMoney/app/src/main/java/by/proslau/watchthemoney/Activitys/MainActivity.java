package by.proslau.watchthemoney.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import by.proslau.watchthemoney.R;
import by.proslau.watchthemoney.dialog.BudgetDialog;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{

    SharedPreferences sPref;

    private static final String APP_PREFERENCE = "WTMPreference";
    private static final String BUDGET = "start_budget";
    private static final String CURRENT_BUDGET = "current_budget";
    private static final String SPENT_BUDGET = "spent_budget";
    private static final String MONEY_BOX_BUDGET = "money_box_budget";
    private static final String YOUR_DEPTS_BUDGET = "your_depts_budget";
    private static final String DEPT_BUDGET = "dept_budget";

    TextView textViewStartBalance;
    TextView textViewSpentBalance;
    TextView textViewCurrentBalance;
    TextView textViewMoneyBoxBalance;
    TextView textViewYourDeptsBalance;
    TextView textViewDeptsBalance;

    Button btnChangeStartBalance;

    SwipeRefreshLayout swipeRefreshLayout;

    double startBalance;
    double currentBalance;
    double spentBalance;
    double moneyBoxBalance;
    double yourDeptsBalance;
    double deptsBalance;

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
        textViewMoneyBoxBalance = (TextView) findViewById(R.id.tv_main_money_box_num);
        textViewYourDeptsBalance = (TextView) findViewById(R.id.tv_main_your_depts_num);
        textViewDeptsBalance = (TextView) findViewById(R.id.tv_main_depts_num);

        btnChangeStartBalance = (Button) findViewById(R.id.main_change_start_balance);
        btnChangeStartBalance.setOnClickListener(this);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED);


        loadAll();

    }

    void loadAll() {
        sPref = getSharedPreferences(APP_PREFERENCE, MODE_PRIVATE);
        try {
            String a = sPref.getString(BUDGET, "");
            startBalance = Double.parseDouble(a);
            a = sPref.getString(CURRENT_BUDGET, "");
            currentBalance = Double.parseDouble(a);
            a = sPref.getString(SPENT_BUDGET, "");
            spentBalance = Double.parseDouble(a);
            a = sPref.getString(MONEY_BOX_BUDGET, "");
            moneyBoxBalance = Double.parseDouble(a);
            a = sPref.getString(YOUR_DEPTS_BUDGET, "");
            yourDeptsBalance = Double.parseDouble(a);
            a = sPref.getString(DEPT_BUDGET, "");
            deptsBalance = Double.parseDouble(a);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Баланс еще не добавлен", Toast.LENGTH_SHORT).show();
        }
        textViewStartBalance.setText(startBalance + " б.р.");
        textViewCurrentBalance.setText(currentBalance + " б.р.");
        textViewSpentBalance.setText(spentBalance + " б.р.");
        textViewMoneyBoxBalance.setText(moneyBoxBalance + " б.р.");
        textViewYourDeptsBalance.setText(yourDeptsBalance + " б.р.");
        textViewDeptsBalance.setText(deptsBalance + " б.р.");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
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
                loadAll();
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
    protected void onResume(){
        super.onResume();
        Toast.makeText(this, "Потяните для обновления", Toast.LENGTH_SHORT).show();
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
