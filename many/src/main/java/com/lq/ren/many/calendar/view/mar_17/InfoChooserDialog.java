package com.lq.ren.many.calendar.view.mar_17;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.lq.ren.many.R;

import java.util.ArrayList;
import java.util.List;

public class InfoChooserDialog extends Dialog {

    private ListView mListView;
    private ChooserAdapter mAdapter;
    private Listener mListener;

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (mListener != null) {
                mListener.onItemClickListener(position);
            }
            dismiss();
        }
    };

    public InfoChooserDialog(Context context) {
        super(context);
        init();
    }

    public InfoChooserDialog(Context context, int theme) {
        super(context, theme);
        init();
    }

    protected InfoChooserDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setContentView(R.layout.dialog_info_chooser);
        mListView = (ListView) findViewById(R.id.list);
        mAdapter = new ChooserAdapter(getContext());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(mOnItemClickListener);
    }

    public void setData(List<String> data) {
        mAdapter.setData(data);
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    public interface Listener {
        void onItemClickListener(int position);
    }

    private static class ChooserAdapter extends BaseAdapter {
        LayoutInflater mInflater;
        List<String> mData = new ArrayList<String>();

        public ChooserAdapter(Context context) {
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void setData(List<String> data) {
            if (data != null) {
                mData.addAll(data);
                notifyDataSetChanged();
            }
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(android.R.layout.simple_list_item_1, null);
            }

            TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
            textView.setText(mData.get(position));
            textView.setTextColor(Color.BLACK);

            return convertView;
        }
    }
}