package com.example.owner.safermaster;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View view = findViewById(R.id.menu_app);
        view.setOnClickListener(this);
        findViewById(R.id.menu_clear).setOnClickListener(this);
        findViewById(R.id.menu_flux).setOnClickListener(this);
        findViewById(R.id.menu_Spam).setOnClickListener(this);
        findViewById(R.id.menu_virus).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu_app:
                startActivity(new Intent(this, AppManegerActivity.class));
                break;
            case R.id.menu_clear:
                startActivity(new Intent(this, ClearActivity.class));
                break;
            case R.id.menu_flux:
                startActivity(new Intent(this, CrankActivity.class));
                break;
            case R.id.menu_Spam:
                startActivity(new Intent(this, DataInfoActivity.class));
                break;
            case R.id.menu_virus:
                startActivity(new Intent(this, CleanActivity.class));
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
