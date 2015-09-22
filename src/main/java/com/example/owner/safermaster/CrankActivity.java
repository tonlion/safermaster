package com.example.owner.safermaster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.owner.dao.PhoneDao;
import com.example.owner.dao.SMSDao;
import com.example.owner.entity.Phone;
import com.example.owner.entity.SMS;

public class CrankActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crank);
        SMSDao dao = new SMSDao(this);
        dao.insertValues(new SMS("10086", "你好", "12:20"));
        PhoneDao pDao = new PhoneDao(this);
        pDao.insertValues(new Phone("10086", "null", "null", "null"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_crank, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
