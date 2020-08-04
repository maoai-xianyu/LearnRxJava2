package com.mao.cn.learnRxJava2.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mao.cn.learnRxJava2.R;
import com.mao.cn.learnRxJava2.model.Images;
import com.mao.cn.learnRxJava2.model.MovieDetail;
import com.mao.cn.learnRxJava2.utils.tools.FrescoFactoryU;
import com.mao.cn.learnRxJava2.utils.tools.ListU;
import com.mao.cn.learnRxJava2.utils.tools.StringU;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author:  zhangkun .
 * date:    on 2017/8/3.
 */

public class MovieTopAdapter extends RecyclerView.Adapter<MovieTopAdapter.ViewHolder> {

    private Context context;
    private List<MovieDetail> movieDetails;

    public MovieTopAdapter(Context context) {
        this.context = context;
        this.movieDetails = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_movie_top_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        int width = context.getResources().getDimensionPixelOffset(R.dimen.d150);
        int height = context.getResources().getDimensionPixelOffset(R.dimen.d222);

        MovieDetail movieDetail = movieDetails.get(position);

        holder.tvMovieTitle.setText(movieDetail.getTitle());
        holder.tvMovieTitleEng.setText(movieDetail.getOriginal_title());

        Images images = movieDetail.getImages();
        if (images != null && StringU.isNotEmpty(images.getMedium())) {
            holder.svMovieCover.setController(FrescoFactoryU.resize(images.getMedium(), width, height));
        } else {
            holder.svMovieCover.setController(FrescoFactoryU.resize(R.drawable.image_dafault, width, height));
        }

    }

    @Override
    public int getItemCount() {
        return ListU.isEmpty(movieDetails) ? 0 : movieDetails.size();
    }

    public void addMovieList(List<MovieDetail> movieDetails) {
        this.movieDetails.clear();
        this.movieDetails.addAll(movieDetails);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.sv_movie_cover)
        SimpleDraweeView svMovieCover;
        @BindView(R.id.tv_movie_title)
        TextView tvMovieTitle;
        @BindView(R.id.tv_movie_title_eng)
        TextView tvMovieTitleEng;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
