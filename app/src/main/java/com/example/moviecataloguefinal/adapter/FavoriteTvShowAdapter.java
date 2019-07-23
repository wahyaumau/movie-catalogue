package com.example.moviecataloguefinal.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviecataloguefinal.R;
import com.example.moviecataloguefinal.api.ApiClient;
import com.example.moviecataloguefinal.model.TvShow;

import java.util.ArrayList;
import java.util.List;

public class FavoriteTvShowAdapter extends RecyclerView.Adapter<FavoriteTvShowAdapter.FavoriteTvShowViewHolder> implements Filterable {
    private List<TvShow> listTvShow = new ArrayList<>();
    private List<TvShow> fullListTvShow = new ArrayList<>();
    private Context context;
    private FavoriteMovieAdapter.OnItemClickListener onItemClickListener;

    public FavoriteTvShowAdapter(Context context) {
        this.context = context;
    }

    public List<TvShow> getListTvShow() {
        return listTvShow;
    }

    public void setListTvShow(List<TvShow> listTvShow) {
        this.fullListTvShow.clear();
        this.fullListTvShow.addAll(listTvShow);
        this.listTvShow.clear();
        this.listTvShow.addAll(listTvShow);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(FavoriteMovieAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public FavoriteTvShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_favorite_tv_show, viewGroup, false);
        return new FavoriteTvShowViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteTvShowViewHolder favoriteTvShowViewHolder, int i) {
        favoriteTvShowViewHolder.bind(getListTvShow().get(i));
    }

    @Override
    public int getItemCount() {
        return listTvShow.size();
    }

    class FavoriteTvShowViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView textViewFavoriteTvShowTitle;
        Button buttonRemoveTvShow;
        RatingBar ratingBarFavoriteTvShow;

        FavoriteTvShowViewHolder(@NonNull View itemView, final FavoriteMovieAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imageViewFavoriteTvShowPoster);
            textViewFavoriteTvShowTitle = itemView.findViewById(R.id.textViewFavoriteTvShowTitle);
            ratingBarFavoriteTvShow = itemView.findViewById(R.id.ratingBarFavoriteTvShow);
            buttonRemoveTvShow = itemView.findViewById(R.id.buttonRemoveFavoriteTvShow);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });

            buttonRemoveTvShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onRemoveFavoriteClick(position);
                        }
                    }
                }
            });
        }

        void bind(final TvShow tvShow) {
            textViewFavoriteTvShowTitle.setText(tvShow.getName());
            ratingBarFavoriteTvShow.setRating((float) tvShow.getVoteAverage() / 2);
            Glide.with(context).load(ApiClient.IMAGE_URL + tvShow.getPosterPath()).apply(new RequestOptions().override(350, 550))
                    .into(imgPoster);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<TvShow> filteredList = new ArrayList<>();
                if (constraint.toString().isEmpty()) {
                    filteredList = fullListTvShow;
                } else {
                    for (TvShow tvShow : fullListTvShow) {
                        if (tvShow.getOriginalName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            filteredList.add(tvShow);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listTvShow = (List<TvShow>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
