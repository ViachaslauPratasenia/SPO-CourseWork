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
    private static final String BUDGET = "budget";
    EditText text;

    void saveText() {
        sPref = getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putFloat(BUDGET, (float)Double.parseDouble(text.getText().toString()));
        ed.commit();
        Toast.makeText(getContext(), "Text saved", Toast.LENGTH_SHORT).show();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.dialog_budget, null);
        text = (EditText) v.findViewById(R.id.et_dialog_budget);
        //v.findViewById(R.id.btnYes).setOnClickListener(this);
        v.findViewById(R.id.budget_dialog_add).setOnClickListener(this);
        v.findViewById(R.id.budget_dialog_cancel).setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.budget_dialog_add:
                saveText();
                dismiss();
                break;
            case R.id.budget_dialog_cancel:
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

}
