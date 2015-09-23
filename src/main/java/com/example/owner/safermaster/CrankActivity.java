package com.example.owner.safermaster;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.owner.adapter.FragmentAdapter;
import com.example.owner.fragment.CrankFragment;

import java.util.ArrayList;
import java.util.List;

public class CrankActivity extends AppCompatActivity {

    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crank);
        pager = (ViewPager) findViewById(R.id.crank_pager);
        initFragment();
    }

    private void initFragment() {
        List<Fragment> fragments = new ArrayList<>();
        Fragment f = new CrankFragment();
        Bundle b = new Bundle();
        b.putInt("type", 0);
        f.setArguments(b);
        fragments.add(f);
        f = new CrankFragment();
        b = new Bundle();
        b.putInt("type", 1);
        f.setArguments(b);
        fragments.add(f);
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        pager.setAdapter(adapter);
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
