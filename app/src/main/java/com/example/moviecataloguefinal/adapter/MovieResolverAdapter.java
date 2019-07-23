package com.example.moviecataloguefinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviecataloguefinal.R;
import com.example.moviecataloguefinal.activity.DetailResolverActivity;
import com.example.moviecataloguefinal.activity.MovieResolverActivity;
import com.example.moviecataloguefinal.api.ApiClient;
import com.example.moviecataloguefinal.model.Movie;
import com.example.moviecataloguefinal.provider.MovieProvider;

import java.util.ArrayList;
import java.util.List;

public class MovieResolverAdapter extends RecyclerView.Adapter<MovieResolverAdapter.ViewHolder> {
    private Context context;
    private final List<Movie> listMovie = new ArrayList<>();

    public MovieResolverAdapter(Context context) {
        this.context = context;
    }

    public List<Movie> getListMovie() {
        return listMovie;
    }

    public void setListMovie(List<Movie> listMovie) {
        this.listMovie.clear();
        this.listMovie.addAll(listMovie);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_content_resolver, viewGroup, false);
        return new MovieResolverAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(getListMovie().get(i));
    }

    @Override
    public int getItemCount() {
        return getListMovie().size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMovieResolverTitle, textViewMovieResolverDescription;
        Button buttonRemoveMovieResolver;
        ImageView imgPoster;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMovieResolverTitle = itemView.findViewById(R.id.textViewMovieResolverTitle);
            textViewMovieResolverDescription = itemView.findViewById(R.id.textViewMovieResolverDescription);
            buttonRemoveMovieResolver = itemView.findViewById(R.id.buttonRemoveMovieResolver);
            imgPoster = itemView.findViewById(R.id.imageViewMovieResolverPoster);
        }

        void bind(final Movie movie) {
            String description = (movie.getOverview().isEmpty()) ? context.getResources().getString(R.string.no_overview) : movie.getOverview();
            textViewMovieResolverTitle.setText(movie.getTitle());
            textViewMovieResolverDescription.setText(description);
            Glide.with(context).load(ApiClient.IMAGE_URL + movie.getPosterPath()).apply(new RequestOptions().override(350, 550))
                    .into(imgPoster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailResolverActivity.class);
                    intent.putExtra(DetailResolverActivity.EXTRA_MOVIE_RESOLVER, movie);
                    context.startActivity(intent);
                }
            });

            buttonRemoveMovieResolver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(MovieProvider.MOVIE_URI + "/" + movie.getId());
                    context.getContentResolver().delete(uri, null, null);
                    context.getContentResolver().notifyChange(uri, new MovieResolverActivity.DataObserver(new Handler(), context));
                }
            });
        }
    }
}
