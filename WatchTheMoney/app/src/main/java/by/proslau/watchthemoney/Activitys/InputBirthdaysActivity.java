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
 * Created by user on 09.05.2018.
 */

public class InputBirthdaysActivity extends Activity implements View.OnClickListener {
    EditText etName;
    Button btnOK;
    Button btnChoiseData;
    Button btnAddContact;
    TextView textView;

    int DIALOG_DATE = 1;
    int myYear = 2018;
    int myMonth = 00;
    int myDay = 01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_birthdays);

        etName = (EditText) findViewById(R.id.et_birthdays_name);
        btnOK = (Button) findViewById(R.id.input_birthdays_add);
        btnChoiseData = (Button) findViewById(R.id.birthays_choise_data);
        btnOK.setOnClickListener(this);
        btnChoiseData.setOnClickListener(this);

        btnAddContact = (Button) findViewById(R.id.birthdays_add_contact);
        btnAddContact.setOnClickListener(this);

        textView = (TextView) findViewById(R.id.tv_birthdays_choise_date);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.input_birthdays_add:
                if(etName.getText().toString().equals("") && textView.getText().toString().equals("")){
                    Toast.makeText(this, "Введите имя и дату", Toast.LENGTH_LONG).show();
                }
                else{
                    String inputDate = "" + myYear + "." + myMonth + "." + myDay;
                    intent.putExtra("name", etName.getText().toString());
                    intent.putExtra("date", inputDate);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
            case R.id.birthays_choise_data:
                showDialog(DIALOG_DATE);
                break;
            case R.id.birthdays_add_contact:
                Intent contactIntent = new Intent(this, ContactActivity.class);
                startActivityForResult(contactIntent, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(data == null) return;
        String name = data.getStringExtra("contact");
        etName.setText(name);
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
