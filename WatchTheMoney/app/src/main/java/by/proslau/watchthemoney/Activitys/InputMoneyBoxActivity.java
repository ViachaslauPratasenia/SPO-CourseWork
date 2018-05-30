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

import java.util.Calendar;

import by.proslau.watchthemoney.R;

/**
 * Created by user on 10.05.2018.
 */

public class InputMoneyBoxActivity extends Activity implements View.OnClickListener{
    private EditText etMoney;
    private Button btnOK;
    private Button btnChoiceDate;
    private TextView textView;
    private EditText etNote;

    private Calendar calendar = Calendar.getInstance();

    private int DIALOG_DATE = 1;
    private int myYear = calendar.get(Calendar.YEAR);
    private int myMonth = calendar.get(Calendar.MONTH) + 1;
    private int myDay = calendar.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_money_box);

        etMoney = (EditText) findViewById(R.id.et_money_box_money);
        btnOK = (Button) findViewById(R.id.input_money_box_add);
        btnChoiceDate = (Button) findViewById(R.id.money_box_choise_date);
        btnOK.setOnClickListener(this);
        btnChoiceDate.setOnClickListener(this);
        textView = (TextView) findViewById(R.id.tv_money_box_choise_date);
        etNote = (EditText) findViewById(R.id.et_money_box_note);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.input_money_box_add:
                try {
                    double num = Math.rint(100.0 * Double.parseDouble(etMoney.getText().toString())) / 100.0;
                    if(num > Math.pow(10,4)){
                        Toast.makeText(this, "Слишком много, не кажется?",
                                Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else{
                        if(etMoney.getText().toString().equals("") && textView.getText().toString().equals("")){
                            Toast.makeText(this, "Введите сумму и дату",
                                    Toast.LENGTH_LONG).show();
                        }
                        else{
                            String inputDate = "" + myYear + "." + myMonth + "." + myDay;
                            intent.putExtra("money", num);
                            intent.putExtra("date", inputDate);
                            intent.putExtra("note", etNote.getText().toString());
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                        break;
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Неправильно введено число",
                            Toast.LENGTH_LONG).show();
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
