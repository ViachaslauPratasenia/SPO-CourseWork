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
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import by.proslau.watchthemoney.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

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
        loadAll();

    }

    public void loadAll() {
        sPref = getSharedPreferences(APP_PREFERENCE, MODE_PRIVATE);
        try {
            String startBalance = Math.rint(100.0 * Double.parseDouble(sPref.getString
                    (BUDGET, ""))) / 100.0 + " б.р.";
            String currentBalance = Math.rint(100.0 * Double.parseDouble(sPref.getString
                    (CURRENT_BUDGET, ""))) / 100.0 + " б.р.";
            String spentBalance = Math.rint(100.0 * Double.parseDouble(sPref.getString
                    (SPENT_BUDGET, ""))) / 100.0 + " б.р.";
            String moneyBoxBalance = Math.rint(100.0 * Double.parseDouble(sPref.getString
                    (MONEY_BOX_BUDGET, ""))) / 100.0 + " б.р.";
            String yourDeptsBalance = Math.rint(100.0 * Double.parseDouble(sPref.getString
                    (YOUR_DEPTS_BUDGET, ""))) / 100.0 + " б.р.";
            String deptsBalance = Math.rint(100.0 * Double.parseDouble(sPref.getString
                    (DEPT_BUDGET, ""))) / 100.0 + " б.р.";

            textViewStartBalance.setText(startBalance);
            textViewCurrentBalance.setText(currentBalance);
            textViewSpentBalance.setText(spentBalance);
            textViewMoneyBoxBalance.setText(moneyBoxBalance);
            textViewYourDeptsBalance.setText(yourDeptsBalance);
            textViewDeptsBalance.setText(deptsBalance);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Число еще не добавлено", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_change_start_balance:
                Intent intent = new Intent(this, BudgetActivity.class);
                startActivity(intent);
                break;
        }
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
    protected void onResume(){
        super.onResume();
        loadAll();
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
