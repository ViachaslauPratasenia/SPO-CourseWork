package by.proslau.watchthemoney.context_menus;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import by.proslau.watchthemoney.R;
import by.proslau.watchthemoney.contacts.Contact;
import by.proslau.watchthemoney.contacts.ContactWork;
import by.proslau.watchthemoney.dialog.DialogCategory;

/**
 * Created by user on 01.05.2018.
 */

public class ContactContextMenu extends AppCompatActivity {

    ArrayList<Contact> arrayList = ContactWork.getAll(this);

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo contextMenuInfo){

    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){

        }
        return super.onContextItemSelected(item);
    }
}
