package by.proslau.watchthemoney.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import by.proslau.watchthemoney.ConstainsPreference;
import by.proslau.watchthemoney.R;

import static by.proslau.watchthemoney.ConstainsPreference.*;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private SharedPreferences sPref;
    private TextView tvStart;
    private TextView tvSpent;
    private TextView tvCurrent;
    private TextView tvMoneyBox;
    private TextView tvYourDepts;
    private TextView tvDepts;

    private Button btnChangeBalance;

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

        tvStart = (TextView) findViewById(R.id.tv_main_start_balance_num);
        tvSpent = (TextView) findViewById(R.id.tv_main_spent_balance_num);
        tvCurrent = (TextView) findViewById(R.id.tv_main_current_balance_num);
        tvMoneyBox = (TextView) findViewById(R.id.tv_main_money_box_num);
        tvYourDepts = (TextView) findViewById(R.id.tv_main_your_depts_num);
        tvDepts = (TextView) findViewById(R.id.tv_main_depts_num);

        btnChangeBalance = (Button) findViewById(R.id.main_change_start_balance);
        btnChangeBalance.setOnClickListener(this);
        loadAll();

    }

    public void loadAll() {
        sPref = getSharedPreferences(ConstainsPreference.APP_PREFERENCE, MODE_PRIVATE);
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

            tvStart.setText(startBalance);
            tvCurrent.setText(currentBalance);
            tvSpent.setText(spentBalance);
            tvMoneyBox.setText(moneyBoxBalance);
            tvYourDepts.setText(yourDeptsBalance);
            tvDepts.setText(deptsBalance);
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
