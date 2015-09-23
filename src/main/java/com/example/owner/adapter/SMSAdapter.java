package com.example.owner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.owner.entity.SMS;
import com.example.owner.safermaster.R;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Owner on 2015/9/22.
 */
public class SMSAdapter extends BaseAdapter {

    private List<SMS> smses;
    private Context context;

    public SMSAdapter(Context context, List<SMS> smses) {
        this.context = context;
        this.smses = smses;
    }

    @Override
    public int getCount() {
        return smses.size();
    }

    @Override
    public Object getItem(int i) {
        return smses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_sms_item, null);
            holder = new ViewHolder();
            holder.phoneNumber = (TextView) view.findViewById(R.id.sms_phone);
            holder.time = (TextView) view.findViewById(R.id.sms_time);
            holder.message = (TextView) view.findViewById(R.id.sms_message);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        SMS sms = smses.get(i);
        holder.phoneNumber.setText(sms.getPhoneNumber());
        holder.time.setText(sms.getDate());
        holder.message.setText(sms.getMessage());
        return view;
    }

    private class ViewHolder {
        private TextView phoneNumber;
        private TextView time;
        private TextView message;
    }
}
