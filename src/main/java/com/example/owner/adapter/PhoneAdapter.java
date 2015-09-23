package com.example.owner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.owner.entity.Phone;
import com.example.owner.safermaster.R;

import java.util.List;

/**
 * Created by Owner on 2015/9/22.
 */
public class PhoneAdapter extends BaseAdapter {

    private List<Phone> phones;
    private Context context;

    public PhoneAdapter(Context context, List<Phone> phones) {
        this.context = context;
        this.phones = phones;
    }

    @Override
    public int getCount() {
        return phones.size();
    }

    @Override
    public Object getItem(int i) {
        return phones.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_phone_item, null);
            holder = new ViewHolder();
            holder.phone = (TextView) view.findViewById(R.id.phone_number);
            holder.time = (TextView) view.findViewById(R.id.phone_time);
            holder.name = (TextView) view.findViewById(R.id.phone_name);
            holder.attribution = (TextView) view.findViewById(R.id.phone_attribution);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Phone p = phones.get(i);
        holder.phone.setText(p.getPhoneNumber());
        holder.time.setText(p.getDate());
        holder.name.setText(p.getName());
        holder.attribution.setText(p.getAttribution());
        return view;
    }

    private class ViewHolder {
        private TextView phone;
        private TextView name;
        private TextView time;
        private TextView attribution;
    }
}
