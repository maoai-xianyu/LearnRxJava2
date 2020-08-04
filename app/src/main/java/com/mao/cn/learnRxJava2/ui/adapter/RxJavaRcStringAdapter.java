package com.mao.cn.learnRxJava2.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mao.cn.learnRxJava2.R;
import com.mao.cn.learnRxJava2.utils.tools.ListU;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author:  zhangkun .
 * date:    on 2017/8/3.
 */

public class RxJavaRcStringAdapter extends RecyclerView.Adapter<RxJavaRcStringAdapter.ViewHolder> {

    private Context context;
    private List<String> strings;

    public RxJavaRcStringAdapter(Context context) {
        this.context = context;
        this.strings = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_rxjava_binding_string, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String str = strings.get(position);
        holder.tvShow.setText(str);

    }

    @Override
    public int getItemCount() {
        return ListU.isEmpty(strings) ? 0 : strings.size();
    }

    public void addStringList(List<String> stringList) {
        this.strings.clear();
        this.strings.addAll(stringList);
        notifyDataSetChanged();
    }


    public void clear() {
        this.strings.clear();
        notifyDataSetChanged();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_show)
        TextView tvShow;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
