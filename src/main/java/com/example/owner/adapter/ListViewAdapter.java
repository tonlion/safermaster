package com.example.owner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.example.owner.safermaster.R;

/**
 * Created by Owner on 2015/9/30.
 */
public class ListViewAdapter extends BaseSwipeAdapter {

    private Context mContext;

    public ListViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getSwipeLayoutResourceId(int i) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int i, ViewGroup viewGroup) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_example, null);
        SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(i));
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                super.onOpen(layout);
            }
        });
        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout swipeLayout, boolean b) {
                Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });
        v.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "click delete", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    @Override
    public void fillValues(int i, View view) {
        TextView t = (TextView) view.findViewById(R.id.position);
        t.setText((i + 1) + ".");
    }

    @Override
    public int getCount() {
        return 50;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
}
