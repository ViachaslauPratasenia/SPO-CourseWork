package by.proslau.watchthemoney.Activitys;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import by.proslau.watchthemoney.R;

import static by.proslau.watchthemoney.ConstainsPreference.*;

/**
 * Created by user on 26.05.2018.
 */

public class BudgetActivity extends Activity implements View.OnClickListener {

    private SharedPreferences sPref;
    private EditText text;
    private double num;

    private Button btnAdd, btnSub, btnSet, btnReset, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_budget);
        btnAdd = (Button) findViewById(R.id.budget_act_add);
        btnSub = (Button) findViewById(R.id.budget_act_sub);
        btnSet = (Button) findViewById(R.id.budget_act_set);
        btnReset = (Button) findViewById(R.id.budget_act_reset);
        btnCancel = (Button) findViewById(R.id.budget_act_cancel);

        btnAdd.setOnClickListener(this);
        btnSub.setOnClickListener(this);
        btnSet.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        text = (EditText) findViewById(R.id.et_dialog_budget);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.budget_act_add:
                try {
                    num = Math.rint(100.0 * Double.parseDouble(text.getText().toString())) / 100.0;
                    if(num < 10000) {
                        addBalance(num);
                        finish();
                    }else {
                        Toast.makeText(this, "Слишком большое число",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Введите число", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.budget_act_sub:
                try {
                    num = Math.rint(100.0 * Double.parseDouble(text.getText().toString())) / 100.0;
                    if(num < 10000) {
                        subBalance(num);
                        finish();
                    }else {
                        Toast.makeText(this, "Слишком большое число",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Введите число", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.budget_act_set:
                try {
                    num = Math.rint(100.0 * Double.parseDouble(text.getText().toString())) / 100.0;
                    if(num < 100000) {
                        saveStartBalance(num);
                        saveCurrentBalance(num);
                        saveSpentResetBalance();
                        saveMoneyBoxBalance();
                        saveYourDeptsBalance();
                        saveDeptsBalance();
                        finish();
                    }else {
                        Toast.makeText(this, "Слишком большое число",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Введите число", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.budget_act_reset:
                saveStartBalance(0);
                saveCurrentBalance(0);
                saveSpentResetBalance();
                finish();
                break;
            case R.id.budget_act_cancel:
                finish();
                break;
        }
    }

    public void saveStartBalance(double num) {
        sPref = getSharedPreferences(APP_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(BUDGET, num + "");
        ed.commit();
    }

    public void saveMoneyBoxBalance() {
        sPref = getSharedPreferences(APP_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(MONEY_BOX_BUDGET, 0 + "");
        ed.commit();
    }

    public void saveYourDeptsBalance() {
        sPref = getSharedPreferences(APP_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(YOUR_DEPTS_BUDGET, 0 + "");
        ed.commit();
    }

    public void saveDeptsBalance() {
        sPref = getSharedPreferences(APP_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(DEPT_BUDGET, 0 + "");
        ed.commit();
    }

    void saveSpentResetBalance(){
        sPref = getSharedPreferences(APP_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SPENT_BUDGET, 0 + "");
        ed.commit();
    }

    public void saveCurrentBalance(double num){
        sPref = getSharedPreferences(APP_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(CURRENT_BUDGET, num + "");
        ed.commit();
    }

    public void addBalance(double num){
        double temp;
        sPref = getSharedPreferences(APP_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        try {
            temp = Double.parseDouble(sPref.getString(CURRENT_BUDGET, ""));
            temp += num;
            ed.putString(CURRENT_BUDGET, temp + "");
            ed.commit();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();
        }
    }

    public void subBalance(double num){
        double temp;
        sPref = getSharedPreferences(APP_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        try {
            temp = Double.parseDouble(sPref.getString(CURRENT_BUDGET, ""));
            temp -= num;
            ed.putString(CURRENT_BUDGET, temp + "");
            ed.commit();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();
        }
    }
}
