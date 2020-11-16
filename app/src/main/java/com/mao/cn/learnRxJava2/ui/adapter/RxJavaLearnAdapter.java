package com.mao.cn.learnRxJava2.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.mao.cn.learnRxJava2.R;
import com.mao.cn.learnRxJava2.contants.ValueMaps.ClickTime;
import com.mao.cn.learnRxJava2.utils.tools.ListU;
import com.mao.cn.learnRxJava2.utils.tools.LogU;
import com.mao.cn.learnRxJava2.utils.tools.StringU;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

/**
 * author:  zhangkun .
 * date:    on 2017/8/3.
 */

public class RxJavaLearnAdapter extends RecyclerView.Adapter<RxJavaLearnAdapter.ViewHolder> {

    private Context context;
    private List<String> strings;
    private BtnClickListener listener;

    public RxJavaLearnAdapter(Context context) {
        this.context = context;
        this.strings = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_rxjava_learn_detail, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String str = strings.get(position);
        if (StringU.equals(str, "------------------")) {
            holder.tvShow.setVisibility(View.GONE);
            holder.vline.setVisibility(View.VISIBLE);
        } else {
            holder.vline.setVisibility(View.GONE);
            holder.tvShow.setVisibility(View.VISIBLE);
            holder.tvShow.setText(str);
        }

        Disposable subscribe = RxView.clicks(holder.tvShow).throttleFirst(ClickTime.BREAK_TIME_MILLISECOND, TimeUnit
            .MILLISECONDS).subscribe(aVoid -> {
            listener.showResult(str);
        }, throwable -> {
            LogU.e(throwable.getMessage());
        });

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

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_show)
        TextView tvShow;
        @BindView(R.id.v_line)
        View vline;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public void addListener(BtnClickListener listener) {
        this.listener = listener;
    }

    public interface BtnClickListener {
        void showResult(String str);
    }
}
