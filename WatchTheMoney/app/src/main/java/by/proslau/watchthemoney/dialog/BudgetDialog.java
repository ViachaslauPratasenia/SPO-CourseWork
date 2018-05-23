package by.proslau.watchthemoney.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import by.proslau.watchthemoney.R;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by user on 20.05.2018.
 */

public class BudgetDialog extends DialogFragment implements View.OnClickListener {
    SharedPreferences sPref;
    private static final String APP_PREFERENCE = "WTMPreference";
    private static final String BUDGET = "start_budget";
    private static final String CURRENT_BUDGET = "current_budget";
    private static final String SPENT_BUDGET = "spent_budget";
    private static final String MONEY_BOX_BUDGET = "money_box_budget";
    private static final String YOUR_DEPTS_BUDGET = "your_depts_budget";
    private static final String DEPT_BUDGET = "dept_budget";
    EditText text;
    double num;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Budget");
        View v = inflater.inflate(R.layout.dialog_budget, null);
        text = (EditText) v.findViewById(R.id.et_dialog_budget);
        //v.findViewById(R.id.btnYes).setOnClickListener(this);
        v.findViewById(R.id.budget_dialog_add).setOnClickListener(this);
        v.findViewById(R.id.budget_dialog_cancel).setOnClickListener(this);
        v.findViewById(R.id.budget_dialog_sub).setOnClickListener(this);
        v.findViewById(R.id.budget_dialog_set).setOnClickListener(this);
        v.findViewById(R.id.budget_dialog_reset).setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.budget_dialog_add:
                try {
                    num = Double.parseDouble(text.getText().toString());
                    addBalance(num);
                    dismiss();
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Введите число", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.budget_dialog_sub:
                try {
                    num = Double.parseDouble(text.getText().toString());
                    subBalance(num);
                    dismiss();
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Введите число", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.budget_dialog_cancel:
                dismiss();
                break;
            case R.id.budget_dialog_set:
                try {
                    num = Double.parseDouble(text.getText().toString());
                    saveStartBalance(num);
                    saveCurrentBalance(num);
                    saveSpentResetBalance();
                    saveMoneyBoxBalance();
                    saveYourDeptsBalance();
                    saveDeptsBalance();
                    dismiss();
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Введите число", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.budget_dialog_reset:
                saveStartBalance(0);
                saveCurrentBalance(0);
                saveSpentResetBalance();
                saveMoneyBoxBalance();
                saveYourDeptsBalance();
                saveDeptsBalance();
                dismiss();
                break;
        }
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    void saveStartBalance(double num) {
        sPref = getActivity().getSharedPreferences(APP_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(BUDGET, num + "");
        ed.commit();
    }

    void saveMoneyBoxBalance() {
        sPref = getActivity().getSharedPreferences(APP_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(MONEY_BOX_BUDGET, 0 + "");
        ed.commit();
    }

    void saveYourDeptsBalance() {
        sPref = getActivity().getSharedPreferences(APP_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(YOUR_DEPTS_BUDGET, 0 + "");
        ed.commit();
    }

    void saveDeptsBalance() {
        sPref = getActivity().getSharedPreferences(APP_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(DEPT_BUDGET, 0 + "");
        ed.commit();
    }

    void saveSpentResetBalance(){
        sPref = getActivity().getSharedPreferences(APP_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SPENT_BUDGET, 0 + "");
        ed.commit();
    }

    void saveCurrentBalance(double num){
        sPref = getActivity().getSharedPreferences(APP_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(CURRENT_BUDGET, num + "");
        ed.commit();
    }

    void addBalance(double num){
        double temp;
        sPref = getActivity().getSharedPreferences(APP_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        try {
            temp = Double.parseDouble(sPref.getString(CURRENT_BUDGET, ""));
            temp += num;
            ed.putString(CURRENT_BUDGET, temp + "");
            ed.commit();
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
        }
    }

    void subBalance(double num){
        double temp;
        sPref = getActivity().getSharedPreferences(APP_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        try {
            temp = Double.parseDouble(sPref.getString(CURRENT_BUDGET, ""));
            temp -= num;
            ed.putString(CURRENT_BUDGET, temp + "");
            ed.commit();
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
        }
    }

}
