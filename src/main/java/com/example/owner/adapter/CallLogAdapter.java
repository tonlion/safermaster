package com.example.owner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.dao.BlackListDao;
import com.example.owner.dao.GlobalDao;
import com.example.owner.database.TableManager;
import com.example.owner.entity.BlackList;
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
        SimpleDateFormat format = new SimpleDateFormat("[MM/dd HH:mm]");
        final CallLog log = logs.get(i);
        holder.name.setText(log.getName());
        long time = Long.parseLong(log.getDate());
        String date = format.format(time);
        holder.time.setText(date + log.getType());
        if (log.isCheck()) {
            holder.check.setChecked(true);
        } else {
            holder.check.setChecked(false);
        }
        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox box = (CheckBox) view;
                log.setIsCheck(box.isChecked());
                BlackListDao blackListDao = new BlackListDao(context);
                GlobalDao globalDao = new GlobalDao(context, TableManager.BlackListTable.TABLE_NAME);
                BlackList blackList = new BlackList();
                blackList.setName(log.getName());
                blackList.setPhoneNumber(log.getNumber());
                if (box.isChecked()) {
                    blackListDao.insertValues(blackList);
                    Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
                } else {
                    globalDao.deleteUser(new String[]{TableManager.
                            BlackListTable.COL_PHONE_NUMBER}, new Object[]{log.getNumber()});
                    Toast.makeText(context, "取消添加", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private class ViewHolder {

        private TextView name;
        private TextView time;
        private CheckBox check;
    }
}
