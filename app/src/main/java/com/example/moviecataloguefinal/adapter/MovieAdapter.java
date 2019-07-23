package com.example.moviecataloguefinal.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviecataloguefinal.R;
import com.example.moviecataloguefinal.api.ApiClient;
import com.example.moviecataloguefinal.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.CardViewViewHolder> implements Filterable {
    private List<Movie> listMovie = new ArrayList<>();
    private List<Movie> fullListMovie = new ArrayList<>();
    private Context context;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onAddFavoriteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public List<Movie> getListMovie() {
        return listMovie;
    }

    public void setListMovie(List<Movie> listMovie) {
        this.fullListMovie.clear();
        this.fullListMovie.addAll(listMovie);
        this.listMovie.clear();
        this.listMovie.addAll(listMovie);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new MovieAdapter.CardViewViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.CardViewViewHolder cardViewViewHolder, int i) {
        cardViewViewHolder.bind(getListMovie().get(i));
    }

    @Override
    public int getItemCount() {
        return getListMovie().size();
    }

    class CardViewViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tVMovieTitle;
        ImageButton btnLike;
        RatingBar ratingBar;

        CardViewViewHolder(@NonNull View itemView, final OnItemClickListener onItemClickListener) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imageViewMoviePoster);
            tVMovieTitle = itemView.findViewById(R.id.textViewMovieTitle);
            btnLike = itemView.findViewById(R.id.buttonLike);
            ratingBar = itemView.findViewById(R.id.ratingBarMovie);

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

            btnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onAddFavoriteClick(position);
                        }
                    }
                }
            });
        }

        void bind(final Movie movie) {
            Glide.with(context).load(ApiClient.IMAGE_URL + movie.getBackdropPath()).apply(new RequestOptions().override(350, 550))
                    .into(imgPoster);
            tVMovieTitle.setText(movie.getTitle());
            ratingBar.setRating((float) movie.getVoteAverage() / 2);
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
                listMovie = (ArrayList<Movie>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
