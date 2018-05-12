package by.proslau.watchthemoney.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import by.proslau.watchthemoney.R;

/**
 * Created by user on 07.05.2018.
 */

public class InputDeptorActivity extends Activity implements View.OnClickListener {

    EditText etName;
    EditText etMoney;
    RadioButton yoursDepts;
    RadioButton depts;
    Button btnOK;
    int check;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_deptor);
        etName = (EditText) findViewById(R.id.et_arrears_name);
        etMoney = (EditText) findViewById(R.id.et_arrears_money);
        yoursDepts = (RadioButton) findViewById(R.id.yours_depts);
        depts = (RadioButton) findViewById(R.id.depts);
        btnOK = (Button) findViewById(R.id.input_deptor_add);
        btnOK.setOnClickListener(this);

        radioGroup = (RadioGroup) findViewById(R.id.input_deptor_rgroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.yours_depts:
                        check = 1;
                        break;
                    case R.id.depts:
                        check = 0;
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        try {
            double money = Double.parseDouble(etMoney.getText().toString());
            if(money < 0){
                Toast.makeText(this, "Долг не может быть меньше нуля", Toast.LENGTH_LONG).show();
                etMoney.setText(null);
            }
            else if(etName.getText().toString().equals("")){
                Toast.makeText(this, "Введите имя", Toast.LENGTH_LONG).show();
            }
            else{
                intent.putExtra("name", etName.getText().toString());
                intent.putExtra("money", money);
                intent.putExtra("choise", check);
                setResult(RESULT_OK, intent);
                finish();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Неправильно введено число", Toast.LENGTH_LONG).show();
        }
    }
}
