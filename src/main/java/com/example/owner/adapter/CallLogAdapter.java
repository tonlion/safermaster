package com.example.owner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.owner.entity.CallLog;
import com.example.owner.safermaster.R;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Owner on 2015/9/23.
 */
public class CallLogAdapter extends BaseAdapter {

    private List<CallLog> logs;
    private Context context;

    public CallLogAdapter(Context context, List<CallLog> logs) {
        this.context = context;
        this.logs = logs;
    }

    @Override
    public int getCount() {
        return logs.size();
    }

    @Override
    public Object getItem(int i) {
        return logs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_contact_item, null);
            holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.contact_name);
            holder.time = (TextView) view.findViewById(R.id.contact_number);
            holder.check = (CheckBox) view.findViewById(R.id.contact_check);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        CallLog log = logs.get(i);
        holder.name.setText(log.getName());
        SimpleDateFormat format = new SimpleDateFormat("[MM/dd HH:mm]");
        long time = Long.parseLong(log.getDate());
        String date = format.format(time);
        holder.time.setText(date + log.getType());
        return view;
    }

    private class ViewHolder {

        private TextView name;
        private TextView time;
        private CheckBox check;
    }
}
