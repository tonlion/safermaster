package com.example.owner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.owner.entity.Cache;
import com.example.owner.safermaster.R;
import com.example.owner.tools.StorageUtil;

import java.util.List;

/**
 * Created by Owner on 2015/9/29.
 */
public class CacheAdapter extends BaseAdapter {
    private List<Cache> caches;
    private Context context;

    public CacheAdapter(List<Cache> caches, Context context) {
        this.caches = caches;
        this.context = context;
    }

    @Override
    public int getCount() {
        return caches.size();
    }

    @Override
    public Object getItem(int i) {
        return caches.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_liuliang_cache, null);
            holder = new ViewHolder();
            holder.image = (ImageView) view.findViewById(R.id.liu_image);
            holder.name = (TextView) view.findViewById(R.id.liu_name);
            holder.size = (TextView) view.findViewById(R.id.liu_size);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Cache cache = caches.get(i);
        holder.image.setImageDrawable(cache.getIcon());
        holder.name.setText(cache.getAppName());
        holder.size.setText(cache.getCacheSize());
        return view;
    }

    private class ViewHolder {
        private ImageView image;
        private TextView name;
        private TextView size;
    }
}
