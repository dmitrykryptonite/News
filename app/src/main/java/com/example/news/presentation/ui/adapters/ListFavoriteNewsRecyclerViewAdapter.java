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
import com.example.news.entities.FavoriteArticle;
import com.example.news.presentation.view.FavoriteNewsActivity;

import java.util.ArrayList;
import java.util.List;

public class ListFavoriteNewsRecyclerViewAdapter extends
        RecyclerView.Adapter<ListFavoriteNewsRecyclerViewAdapter.ViewHolder> {
    private List<FavoriteArticle> favoriteArticles = new ArrayList<>();
    private FavoriteNewsActivity favoriteActivity;

    public ListFavoriteNewsRecyclerViewAdapter(FavoriteNewsActivity favoriteActivity) {
        this.favoriteActivity = favoriteActivity;
    }

    public void updateFavoriteNewsList(List<FavoriteArticle> favoriteArticles) {
        this.favoriteArticles = favoriteArticles;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListFavoriteNewsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListFavoriteNewsRecyclerViewAdapter.ViewHolder holder,
                                 int position) {
        final FavoriteArticle favoriteArticle = favoriteArticles.get(position);
        Glide.with(App.getApp())
                .load(favoriteArticle.getPathToImage())
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
        holder.tvTitle.setText(favoriteArticle.getTitle());
        holder.tvDesc.setText(favoriteArticle.getDescription());
        holder.tvAuthor.setText(favoriteArticle.getAuthor());
        holder.tvPublishedAt.setText(Utils.DateFormat(favoriteArticle.getDate()));
        holder.tvSource.setText(favoriteArticle.getAppbarTitle());
        holder.tvTime.setText(String.format("•%s", Utils.DateToTimeFormat(favoriteArticle.getDate())));
        //holder.cardView.setOnClickListener(v -> mainActivity.openDetailScreen(apiArticle));
    }

    @Override
    public int getItemCount() {
        return favoriteArticles.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc, tvAuthor, tvPublishedAt, tvSource, tvTime;
        ImageView imgV;
        ProgressBar progressLoadPhoto;
        CardView cardView;

        ViewHolder(@NonNull View itemView) {
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
