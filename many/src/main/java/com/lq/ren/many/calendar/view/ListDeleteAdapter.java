package com.lq.ren.many.calendar.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.lq.ren.many.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lqren on 16/8/11.
 */
public class ListDeleteAdapter extends ArrayAdapter {

    List<String> mDate = new ArrayList<>();

    public ListDeleteAdapter(Context context, int resource) {
        super(context, resource);

    }

    public void setDate(List<String> date) {
        mDate = date;
    }

    public List<String> getDate() {
        return mDate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.custom_listdeleteitem, null);
            view.setTag(position);
        } else {
            view = convertView;
        }
        TextView tv = (TextView) view.findViewById(R.id.text_view);
        tv.setText(getDate().get(position));

        return view;
    }

    @Override
    public int getCount() {
        return mDate.size();
    }

}
