package com.example.owner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.owner.entity.RunningProcess;
import com.example.owner.safermaster.R;
import com.example.owner.tools.StorageUtil;

import java.util.List;

/**
 * Created by Owner on 2015/9/21.
 */
public class ClearAdapter extends BaseAdapter {

    private List<RunningProcess> processes;
    private Context context;
    private OnChooseListener listener;

    public ClearAdapter(Context context, List<RunningProcess> processes) {
        this.context = context;
        this.processes = processes;
    }

    public void setListener(OnChooseListener listener) {
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return processes.size();
    }

    @Override
    public Object getItem(int i) {
        return processes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_clear_item, null);
            holder = new ViewHolder();
            holder.icon = (ImageView) view.findViewById(R.id.clear_item_appicon);
            holder.appName = (TextView) view.findViewById(R.id.clear_item_appName);
            holder.memorySize = (TextView) view.findViewById(R.id.clear_item_memorySize);
            holder.isChecked = (CheckBox) view.findViewById(R.id.clear_item_isChecked);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final RunningProcess process = processes.get(i);
        holder.icon.setImageDrawable(process.getIcon());
        holder.appName.setText(process.getAppName());
        holder.memorySize.setText(StorageUtil.convertStorage(process.getMemorySize()));
        if (holder.isChecked.isChecked()) {
            listener.addItem(process);
        }
        holder.isChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox box = (CheckBox) view;
                if (listener != null) {
                    if (box.isChecked()) {
                        listener.addItem(process);
                    } else {
                        listener.deleteItem(process);
                    }
                }
            }
        });
        return view;
    }

    private class ViewHolder {
        private ImageView icon;
        private TextView appName;
        private TextView memorySize;
        private CheckBox isChecked;
    }

    public interface OnChooseListener {
        public void addItem(RunningProcess process);

        public void deleteItem(RunningProcess process);
    }
}
