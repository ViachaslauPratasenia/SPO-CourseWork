package by.proslau.watchthemoney.Activitys;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import by.proslau.watchthemoney.R;

/**
 * Created by user on 10.05.2018.
 */

public class InputMoneyBoxActivity extends Activity implements View.OnClickListener{
    EditText etMoney;
    Button btnOK;
    Button btnChoiseData;
    TextView textView;
    TextView textViewNote;

    int DIALOG_DATE = 1;
    int myYear = 2018;
    int myMonth = 00;
    int myDay = 01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_money_box);

        etMoney = (EditText) findViewById(R.id.et_money_box_money);
        btnOK = (Button) findViewById(R.id.input_money_box_add);
        btnChoiseData = (Button) findViewById(R.id.money_box_choise_date);
        btnOK.setOnClickListener(this);
        btnChoiseData.setOnClickListener(this);
        textView = (TextView) findViewById(R.id.tv_money_box_choise_date);
        textViewNote = (TextView) findViewById(R.id.et_money_box_note);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.input_money_box_add:
                try {
                    if(etMoney.getText().toString().equals("") && textView.getText().toString().equals("")){
                        Toast.makeText(this, "Введите имя и дату", Toast.LENGTH_LONG).show();
                    }
                    else{
                        String inputDate = "" + myYear + "." + myMonth + "." + myDay;
                        intent.putExtra("money", Double.parseDouble(etMoney.getText().toString()));
                        intent.putExtra("date", inputDate);
                        intent.putExtra("note", textViewNote.getText().toString());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                    break;
                } catch (Exception e) {
                    Toast.makeText(this, "Неправильно введено число", Toast.LENGTH_LONG).show();
                }
            case R.id.money_box_choise_date:
                showDialog(DIALOG_DATE);
                break;
        }
    }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_DATE) {
            DatePickerDialog tpd = new DatePickerDialog(this, myCallBack, myYear, myMonth, myDay);
            return tpd;
        }
        return super.onCreateDialog(id);
    }

    DatePickerDialog.OnDateSetListener myCallBack = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myYear = year;
            myMonth = monthOfYear + 1;
            myDay = dayOfMonth;
            textView.setText("Выбранная дата " + myDay + "/" + myMonth + "/" + myYear);
        }
    };
}
