package com.example.owner.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.owner.adapter.PhoneAdapter;
import com.example.owner.adapter.SMSAdapter;
import com.example.owner.dao.PhoneDao;
import com.example.owner.dao.SMSDao;
import com.example.owner.entity.Phone;
import com.example.owner.entity.SMS;
import com.example.owner.safermaster.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 2015/9/22.
 */
public class CrankFragment extends Fragment {

    private int type;
    private List<SMS> smses;
    private List<Phone> phones;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        type = bundle.getInt("type");
        smses = new ArrayList<>();
        phones = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_list_view, null);
        listView = (ListView) v.findViewById(R.id.list_view);
        loadData();
        return v;
    }

    private void loadData() {
        if (type == 0) {
            //得到SMS数据库
            SMSDao dao = new SMSDao(getActivity());
            smses = dao.findAll();
            SMSAdapter adapter = new SMSAdapter(getActivity(), smses);
            listView.setAdapter(adapter);
        } else if (type == 1) {
            //得到Phone数据库
            PhoneDao dao = new PhoneDao(getActivity());
            phones = dao.findAll();
            PhoneAdapter adapter = new PhoneAdapter(getActivity(), phones);
            listView.setAdapter(adapter);
        }
    }
}
