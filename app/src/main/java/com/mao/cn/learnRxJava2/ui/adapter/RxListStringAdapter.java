package com.mao.cn.learnRxJava2.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mao.cn.learnRxJava2.R;
import com.mao.cn.learnRxJava2.utils.tools.ListU;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author:  zhangkun .
 * date:    on 2017/8/16.
 */

public class RxListStringAdapter extends BaseAdapter {


    private final LayoutInflater inflater;
    private List<String> stringList;

    public RxListStringAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.stringList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return ListU.isEmpty(stringList) ? 0 : stringList.size();
    }

    @Override
    public Object getItem(int position) {
        return stringList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_rxjava_binding_string, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tvShow.setText(stringList.get(position));
        return convertView;
    }


    public void addStringList(List<String> list) {
        this.stringList.clear();
        this.stringList.addAll(list);
        notifyDataSetChanged();
    }

    public void clear(){
        this.stringList.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.tv_show)
        TextView tvShow;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
