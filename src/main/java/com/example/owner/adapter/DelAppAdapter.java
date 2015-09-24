package com.example.owner.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.owner.entity.RunningProcess;
import com.example.owner.safermaster.R;

import java.util.List;

/**
 * Created by Owner on 2015/9/24.
 */
public class DelAppAdapter extends BaseAdapter {

    private List<RunningProcess> processes;
    private Context context;

    public DelAppAdapter(Context context, List<RunningProcess> processes) {
        this.context = context;
        this.processes = processes;
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
            view = LayoutInflater.from(context).inflate(R.layout.layout_delapp_item, null);
            holder = new ViewHolder();
            holder.icon = (ImageView) view.findViewById(R.id.del_img);
            holder.appName = (TextView) view.findViewById(R.id.del_name);
            holder.version = (TextView) view.findViewById(R.id.del_version);
            holder.uninstall = (Button) view.findViewById(R.id.del_uninstall);
            holder.system = (ImageView) view.findViewById(R.id.del_system);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final RunningProcess process = processes.get(i);
        holder.icon.setImageDrawable(process.getIcon());
        holder.appName.setText(process.getAppName());
        holder.version.setText(process.getVersion());
        if (process.isSystem()) {
            holder.system.setVisibility(View.VISIBLE);
            holder.uninstall.setVisibility(View.INVISIBLE);
        } else {
            holder.system.setVisibility(View.INVISIBLE);
            holder.uninstall.setVisibility(View.VISIBLE);
            holder.uninstall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse("package:" + process.getPackageName());
                    Intent intent = new Intent(Intent.ACTION_DELETE, uri);
                    context.startActivity(intent);
                }
            });
        }
        return view;
    }

    private class ViewHolder {
        private ImageView icon;
        private TextView appName;
        private TextView version;
        private TextView memorySize;
        private Button uninstall;
        private ImageView system;

    }
}
