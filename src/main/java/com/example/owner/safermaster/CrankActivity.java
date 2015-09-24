package com.example.owner.safermaster;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.owner.adapter.FragmentAdapter;
import com.example.owner.fragment.CrankFragment;

import java.util.ArrayList;
import java.util.List;

public class CrankActivity extends AppCompatActivity {

    private FragmentTabHost tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crank);
        tab = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tab.setup(this, getSupportFragmentManager(), R.id.crank_container);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff3EAE19));
        Bundle bundle = new Bundle();
        bundle.putInt("type", 0);
        addTabs("短信记录", "sms", bundle);
        bundle = new Bundle();
        bundle.putInt("type", 1);
        addTabs("来电记录", "phone", bundle);
    }

    /**
     * fragmentTabHost版
     *
     * @param tabName
     * @param tag
     * @param bundle
     */
    private void addTabs(String tabName, String tag, Bundle bundle) {
        TextView tabItem = new TextView(this);
        tabItem.setText(tabName);
        tabItem.setGravity(Gravity.CENTER);
        tabItem.setBackgroundColor(0xff329619);
        tabItem.setTextColor(getResources().getColor(R.color.contact_color));
        tabItem.setTextSize(16);
        FragmentTabHost.TabSpec tabSpec = tab.newTabSpec(tag);
        tabSpec.setIndicator(tabItem);
        tab.addTab(tabSpec, CrankFragment.class, bundle);
    }

    /**
     * 之前viewpager版
     */
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
        // pager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_crank, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.action_contact:
                startActivity(new Intent(getApplicationContext(), ContactionActivity.class));
                break;
            case R.id.action_calllog:
                startActivity(new Intent(getApplicationContext(), CallLogActivity.class));
                break;
            case R.id.action_SMS:
                startActivity(new Intent(getApplicationContext(), ShortMessageActivity.class));
                break;
            case R.id.action_blacklist:
                startActivity(new Intent(getApplicationContext(), BlackListActivity.class));
                break;
        }
        return true;
    }
}
