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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviecataloguefinal.R;
import com.example.moviecataloguefinal.api.ApiClient;
import com.example.moviecataloguefinal.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieViewHolder> implements Filterable {
    private List<Movie> listMovie = new ArrayList<>();
    private List<Movie> fullListMovie = new ArrayList<>();
    private Context context;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onRemoveFavoriteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public FavoriteMovieAdapter(Context context) {
        this.context = context;
    }

    public List<Movie> getListMovie() {
        return listMovie;
    }

    public void setListMovie(List<Movie> movies) {
        this.fullListMovie.clear();
        this.fullListMovie.addAll(movies);
        this.listMovie.clear();
        this.listMovie.addAll(movies);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteMovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_favorite_movie, viewGroup, false);
        return new FavoriteMovieAdapter.FavoriteMovieViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMovieViewHolder favoriteMovieViewHolder, int i) {
        favoriteMovieViewHolder.bind(getListMovie().get(i));
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    class FavoriteMovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tVMovieTitle, tVMovieDescription;
        Button buttonRemoveFavorite;

        FavoriteMovieViewHolder(@NonNull View itemView, final OnItemClickListener onItemClickListener) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imageViewFavoriteMoviePoster);
            tVMovieTitle = itemView.findViewById(R.id.textViewFavoriteMovieTitle);
            tVMovieDescription = itemView.findViewById(R.id.textViewFavoriteMovieDescription);
            buttonRemoveFavorite = itemView.findViewById(R.id.buttonRemoveFavoriteMovie);

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

            buttonRemoveFavorite.setOnClickListener(new View.OnClickListener() {
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

        void bind(final Movie movie) {
            String description;
            if (movie.getOverview().isEmpty()) {
                description = context.getResources().getString(R.string.no_overview);
            } else {
                description = movie.getOverview();
            }
            Glide.with(context).load(ApiClient.IMAGE_URL + movie.getBackdropPath()).apply(new RequestOptions().override(350, 550))
                    .into(imgPoster);
            tVMovieTitle.setText(movie.getTitle());
            tVMovieDescription.setText(description);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Movie> filteredList = new ArrayList<>();
                if (constraint.toString().isEmpty()) {
                    filteredList = fullListMovie;
                } else {
                    for (Movie movie : fullListMovie) {
                        if (movie.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            filteredList.add(movie);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listMovie = (List<Movie>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
