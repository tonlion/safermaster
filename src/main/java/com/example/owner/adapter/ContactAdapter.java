package com.example.owner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.owner.entity.Contaction;
import com.example.owner.safermaster.R;

import java.util.List;

/**
 * Created by Owner on 2015/9/23.
 */
public class ContactAdapter extends BaseAdapter {

    private List<Contaction> contactions;
    private Context context;
    private OnClickToAddListener listener;

    public ContactAdapter(List<Contaction> contactions, Context context) {
        this.contactions = contactions;
        this.context = context;
    }

    public void setListener(OnClickToAddListener listener) {
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return contactions.size();
    }

    @Override
    public Object getItem(int i) {
        return contactions.get(i);
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
            holder.number = (TextView) view.findViewById(R.id.contact_number);
            holder.check = (CheckBox) view.findViewById(R.id.contact_check);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final Contaction c = contactions.get(i);
        holder.name.setText(c.getDesplayName());
        holder.number.setText(c.getPhoneNum());
        if (c.getSelected() == 1) {
            holder.check.setChecked(true);
        }
        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox box = (CheckBox) view;
                if (box.isChecked()) {
                    listener.addContact(c);
                } else {
                    listener.removeContact(c);
                }
                c.setSelected(1);
            }
        });
        return view;
    }

    private class ViewHolder {
        private TextView name;
        private TextView number;
        private CheckBox check;
    }

    public interface OnClickToAddListener {
        public void addContact(Contaction contaction);

        public void removeContact(Contaction contaction);
    }
}
