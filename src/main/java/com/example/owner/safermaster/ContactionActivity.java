package com.example.owner.safermaster;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.owner.adapter.ContactAdapter;
import com.example.owner.dao.BlackListDao;
import com.example.owner.entity.BlackList;
import com.example.owner.entity.Contaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 2015/9/23.
 */
public class ContactionActivity extends AppCompatActivity implements
        ContactAdapter.OnClickToAddListener {

    private ListView items;
    private List<Contaction> contactions;
    private ContactAdapter adapter;
    private List<Contaction> blackLists;
    private Button addToList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        items = (ListView) findViewById(R.id.contact_list_view);
        addToList = (Button) findViewById(R.id.contact_add_to_blackList);
        contactions = new ArrayList<>();
        blackLists = new ArrayList<>();
        contactions = contactions();
        adapter = new ContactAdapter(contactions, ContactionActivity.this);
        adapter.setListener(this);
        items.setAdapter(adapter);
        addToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BlackListDao dao = new BlackListDao(ContactionActivity.this);
                for (int i = 0; i < blackLists.size(); i++) {
                    BlackList black = new BlackList(
                            blackLists.get(i).getDesplayName(), blackLists.get(i).getPhoneNum());
                    dao.insertValues(black);
                }
                Toast.makeText(getApplicationContext(), dao.findAll().size() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Contaction> contactions() {
        List<Contaction> contactions = new ArrayList<>();
        ContentResolver resolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = resolver.query(uri, null, null, null, null);
        while (cursor.moveToNext()) {
            Contaction contaction = new Contaction();
            contaction.setContactId(Integer.parseInt(cursor.getString(
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID))));
            contaction.setDesplayName(cursor.getString(
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
            contaction.setPhoneNum(cursor.getString(
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            contaction.setSortKey(cursor.getString(
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.SORT_KEY_PRIMARY)));
            //contaction.setPhotoId(Long.parseLong(cursor.getString(
            //        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_ID))));
            contaction.setLookUpKey(cursor.getString(
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY)));
            contactions.add(contaction);
        }
        cursor.close();
        return contactions;
    }

    @Override
    public void addContact(Contaction contaction) {
        blackLists.add(contaction);
    }

    @Override
    public void removeContact(Contaction contaction) {
        blackLists.remove(contaction);
    }
}
