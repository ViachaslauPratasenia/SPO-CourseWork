package by.proslau.watchthemoney.Activitys;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import by.proslau.watchthemoney.R;

/**
 * Created by user on 19.05.2018.
 */

public class InputCostActivity extends Activity implements View.OnClickListener{
    EditText etMoney;
    Button btnOK;
    Button btnChoiseData;
    TextView textView;
    TextView editTextNote;
    Spinner spinner;
    int spinnerPosition;

    Calendar calendar = Calendar.getInstance();

    int DIALOG_DATE = 1;
    int myYear = calendar.get(Calendar.YEAR);
    int myMonth = calendar.get(Calendar.MONTH);
    int myDay = calendar.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_cost_activity);

        etMoney = (EditText) findViewById(R.id.et_cost_money);
        btnOK = (Button) findViewById(R.id.input_cost_add);
        btnChoiseData = (Button) findViewById(R.id.cost_choise_date);
        textView = (TextView) findViewById(R.id.tv_cost_choise_date);
        editTextNote = (EditText) findViewById(R.id.et_cost_note);
        spinner = (Spinner) findViewById(R.id.spinner_input_cost);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerPosition = i + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnOK.setOnClickListener(this);
        btnChoiseData.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.input_cost_add:
                try {
                    if(etMoney.getText().toString().equals("") && textView.getText().toString().equals("")){
                        Toast.makeText(this, "Введите сумму и дату", Toast.LENGTH_LONG).show();
                    }
                    else{
                        double input = Double.parseDouble(etMoney.getText().toString());
                        if(input < Math.pow(10, 4)){
                            String inputDate = "" + myYear + "." + myMonth + "." + myDay;
                            intent.putExtra("money", input);
                            intent.putExtra("date", inputDate);
                            intent.putExtra("note", editTextNote.getText().toString());
                            intent.putExtra("category", spinnerPosition);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                        else {
                            Toast.makeText(this, "Простите, мы верим, что у вас есть такие деньги",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                } catch (Exception e) {
                    Toast.makeText(this, "Неправильно введено число", Toast.LENGTH_LONG).show();
                }
            case R.id.cost_choise_date:
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
