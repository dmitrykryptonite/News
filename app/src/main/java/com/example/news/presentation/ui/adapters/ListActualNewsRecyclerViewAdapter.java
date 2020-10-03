package com.example.news.presentation.ui.adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.news.R;
import com.example.news.app.App;
import com.example.news.data.utils.Utils;
import com.example.news.entities.data.ApiArticle;
import com.example.news.presentation.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ListActualNewsRecyclerViewAdapter extends
        RecyclerView.Adapter<ListActualNewsRecyclerViewAdapter.ActualViewHolder> {
    private List<ApiArticle> apiArticles = new ArrayList<>();
    private MainActivity mainActivity;

    public ListActualNewsRecyclerViewAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void updateActualNewsList(List<ApiArticle> apiArticles) {
        this.apiArticles.clear();
        this.apiArticles.addAll(apiArticles);
        notifyDataSetChanged();
    }

    public void clearActualNewsList() {
        this.apiArticles.clear();
    }

    @NonNull
    @Override
    public ActualViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv_actual_news, parent, false);
        return new ActualViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActualViewHolder holder,
                                 int position) {
        final ApiArticle apiArticle = apiArticles.get(position);
        Glide.with(App.getApp())
                .load(apiArticle.getUrlToImage())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target, boolean isFirstResource) {
                        FrameLayout.LayoutParams layoutParams =
                                new FrameLayout.LayoutParams(0, 0);
                        layoutParams.setMargins(0, 0, 0, 0);
                        holder.cardView.setLayoutParams(layoutParams);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model,
                                                   Target<Drawable> target, DataSource dataSource,
                                                   boolean isFirstResource) {
                        holder.progressLoadPhoto.setVisibility(View.GONE);
                        return false;
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imgV);
        holder.tvTitle.setText(apiArticle.getTitle());
        holder.tvDesc.setText(apiArticle.getDescription());
        holder.tvAuthor.setText(apiArticle.getAuthor());
        holder.tvPublishedAt.setText(Utils.DateFormat(apiArticle.getPublishedAt()));
        holder.tvSource.setText(apiArticle.getSource().getName());
        holder.tvTime.setText(String.format("•%s", Utils.DateToTimeFormat(apiArticle.getPublishedAt())));
        holder.cardView.setOnClickListener(v -> mainActivity.openDetailActualScreen(apiArticle));
    }

    @Override
    public int getItemCount() {
        return apiArticles.size();
    }

    static class ActualViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc, tvAuthor, tvPublishedAt, tvSource, tvTime;
        ImageView imgV;
        ProgressBar progressLoadPhoto;
        CardView cardView;

        ActualViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvPublishedAt = itemView.findViewById(R.id.tvPublishedAt);
            tvSource = itemView.findViewById(R.id.tvSource);
            tvTime = itemView.findViewById(R.id.tvTime);
            imgV = itemView.findViewById(R.id.imgV);
            progressLoadPhoto = itemView.findViewById(R.id.progressLoadPhoto);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
