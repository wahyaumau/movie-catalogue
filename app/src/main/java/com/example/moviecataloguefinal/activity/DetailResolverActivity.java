package com.example.moviecataloguefinal.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviecataloguefinal.R;
import com.example.moviecataloguefinal.api.ApiClient;
import com.example.moviecataloguefinal.model.Movie;
import com.example.moviecataloguefinal.model.TvShow;

public class DetailResolverActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE_RESOLVER = "com.example.moviecataloguefinal.EXTRA_MOVIE_RESOLVER";
    public static final String EXTRA_TV_SHOW_RESOLVER = "com.example.moviecataloguefinal.EXTRA_TV_SHOW_RESOLVER";
    TextView textViewTitle, textViewDescription, textViewDate;
    ImageView imageViewBackdrop, imageViewPoster;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_resolver);
        textViewTitle = findViewById(R.id.textViewTitleResolverDetail);
        textViewDescription = findViewById(R.id.textViewDescriptionResolverDetail);
        textViewDate = findViewById(R.id.textViewDateResolverDetail);
        imageViewBackdrop = findViewById(R.id.imageViewBackdropResolverDetail);
        imageViewPoster = findViewById(R.id.imageViewPosterResolverDetail);
        ratingBar = findViewById(R.id.ratingBarResolverDetail);
        if (getIntent().getParcelableExtra(EXTRA_MOVIE_RESOLVER) != null){
            Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE_RESOLVER);
            initMovieView(movie);
        }else {
            TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TV_SHOW_RESOLVER);
            initTvShowView(tvShow);
        }
    }

    private void initMovieView(Movie movie){
        String description = (movie.getOverview().isEmpty())? getResources().getString(R.string.no_overview) : movie.getOverview();
        textViewTitle.setText(movie.getTitle());
        textViewDescription.setText(description);
        textViewDate.setText(movie.getReleaseDate().toString());
        Glide.with(getApplicationContext()).load(ApiClient.IMAGE_URL + movie.getBackdropPath()).into(imageViewBackdrop);
        Glide.with(getApplicationContext()).load(ApiClient.IMAGE_URL + movie.getPosterPath()).into(imageViewPoster);
        ratingBar.setRating((float) movie.getVoteAverage()/2);
    }

    private void initTvShowView(TvShow tvShow){
        String description = (tvShow.getOverview().isEmpty())? getResources().getString(R.string.no_overview) : tvShow.getOverview();
        textViewTitle.setText(tvShow.getOriginalName());
        textViewDescription.setText(description);
        textViewDate.setText(tvShow.getFirstAirDate().toString());
        Glide.with(getApplicationContext()).load(ApiClient.IMAGE_URL + tvShow.getBackdropPath()).into(imageViewBackdrop);
        Glide.with(getApplicationContext()).load(ApiClient.IMAGE_URL + tvShow.getPosterPath()).into(imageViewPoster);
        ratingBar.setRating((float) tvShow.getVoteAverage()/2);
    }
}
