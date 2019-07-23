package com.example.moviecataloguefinal.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviecataloguefinal.R;
import com.example.moviecataloguefinal.api.ApiClient;
import com.example.moviecataloguefinal.model.Movie;
import com.example.moviecataloguefinal.model.TvShow;
import com.example.moviecataloguefinal.viewmodel.FavoriteMovieViewModel;
import com.example.moviecataloguefinal.viewmodel.FavoriteTvShowViewModel;

public class DetailMovieActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_MOVIE = "com.example.moviecataloguefinal.EXTRA_MOVIE";
    public static final String EXTRA_TV_SHOW = "com.example.moviecataloguefinal.EXTRA_TV_SHOW";

    private TextView textViewTitleDetail, textViewDescriptionDetail, textViewDateDetail;
    private ImageView imageViewPosterDetail, imageViewBackdropDetail;
    private RatingBar ratingBar;
    private FloatingActionButton floatingActionButton;
    private FavoriteMovieViewModel favoriteMovieViewModel;
    private FavoriteTvShowViewModel favoriteTvShowViewModel;
    private boolean canInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        textViewTitleDetail = findViewById(R.id.textViewTitleDetail);
        textViewDescriptionDetail = findViewById(R.id.textViewDescriptionDetail);
        textViewDateDetail = findViewById(R.id.textViewDateDetail);
        imageViewPosterDetail = findViewById(R.id.imageViewPosterDetail);
        imageViewBackdropDetail = findViewById(R.id.imageViewBackdropDetail);
        ratingBar = findViewById(R.id.ratingBarMovieDetail);
        ProgressBar progressBar = findViewById(R.id.progressBarDetail);
        floatingActionButton = findViewById(R.id.floatingActionButtonFavorite);
        favoriteMovieViewModel = ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);
        favoriteTvShowViewModel = ViewModelProviders.of(this).get(FavoriteTvShowViewModel.class);
        floatingActionButton.setOnClickListener(this);
        initializeStatusCanInsert(isCanInsert());
        progressBar.setVisibility(View.VISIBLE);

        if (getPassedMovie() != null) {
            initMovieView(getPassedMovie());
        } else {
            initTvShowView(getPassedTvShow());
        }
        progressBar.setVisibility(View.INVISIBLE);
    }

    public Movie getPassedMovie() {
        return getIntent().getParcelableExtra(EXTRA_MOVIE);
    }

    public TvShow getPassedTvShow() {
        return getIntent().getParcelableExtra(EXTRA_TV_SHOW);
    }

    public Movie getMovieInDb(Movie movie) {
        return favoriteMovieViewModel.getMovieById(movie.getId());
    }

    public TvShow getTvShowInDb(TvShow tvShow) {
        return favoriteTvShowViewModel.getTvShowById(tvShow.getId());
    }

    public boolean isCanInsert() {
        if (getPassedMovie() != null) {
            return (getMovieInDb(getPassedMovie()) == null);
        } else {
            return (getTvShowInDb(getPassedTvShow()) == null);
        }
    }

    public void initializeStatusCanInsert(boolean status) {
        if (status) {
            canInsert = true;
            floatingActionButton.setImageResource(R.drawable.like);
        } else {
            canInsert = false;
            floatingActionButton.setImageResource(R.drawable.unlike);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.floatingActionButtonFavorite) {
            if (getPassedMovie() != null) {
                if (canInsert) {
                    favoriteMovieViewModel.insertMovie(getPassedMovie());
                    Toast.makeText(getApplicationContext(), String.format(getResources().getString(R.string.message_added_to_database),
                            getPassedMovie().getTitle()), Toast.LENGTH_SHORT).show();
                    floatingActionButton.setImageResource(R.drawable.unlike);
                    canInsert = false;
                } else {
                    Snackbar.make(v, String.format(getResources().getString(R.string.message_remove_confirmation), getPassedMovie().getTitle()), Snackbar.LENGTH_SHORT).
                            setAction(getResources().getString(R.string.yes), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    favoriteMovieViewModel.deleteMovie(getPassedMovie());
                                    Toast.makeText(getApplicationContext(), String.format(getResources().getString(R.string.message_remove_from_database),
                                            getPassedMovie().getTitle()), Toast.LENGTH_SHORT).show();
                                    floatingActionButton.setImageResource(R.drawable.like);
                                    canInsert = true;
                                }
                            }).show();

                }
            } else {
                if (canInsert) {
                    favoriteTvShowViewModel.insertTvShow(getPassedTvShow());
                    Toast.makeText(getApplicationContext(), String.format(getResources().getString(R.string.message_added_to_database),
                            getPassedTvShow().getOriginalName()), Toast.LENGTH_SHORT).show();
                    floatingActionButton.setImageResource(R.drawable.unlike);
                    canInsert = false;
                } else {
                    Snackbar.make(v, String.format(getResources().getString(R.string.message_remove_confirmation), getPassedTvShow().getName()), Snackbar.LENGTH_SHORT).
                            setAction(getResources().getString(R.string.yes), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    favoriteTvShowViewModel.deleteTvShow(getPassedTvShow());
                                    Toast.makeText(getApplicationContext(), String.format(getResources().getString(R.string.message_remove_from_database),
                                            getPassedTvShow().getOriginalName()), Toast.LENGTH_SHORT).show();
                                    floatingActionButton.setImageResource(R.drawable.like);
                                    canInsert = true;
                                }
                            }).show();
                }
            }
        }
    }

    private void initMovieView(Movie movie) {
        String description = (movie.getOverview().isEmpty()) ? getResources().getString(R.string.no_overview) : movie.getOverview();
        textViewTitleDetail.setText(movie.getTitle());
        textViewDescriptionDetail.setText(description);
        textViewDateDetail.setText(movie.getReleaseDate().toString());
        Glide.with(getApplicationContext()).load(ApiClient.IMAGE_URL + movie.getBackdropPath()).into(imageViewBackdropDetail);
        Glide.with(getApplicationContext()).load(ApiClient.IMAGE_URL + movie.getPosterPath()).into(imageViewPosterDetail);
        ratingBar.setRating((float) movie.getVoteAverage() / 2);
    }

    private void initTvShowView(TvShow tvShow) {
        String description = (tvShow.getOverview().isEmpty()) ? getResources().getString(R.string.no_overview) : tvShow.getOverview();
        textViewTitleDetail.setText(tvShow.getOriginalName());
        textViewDescriptionDetail.setText(description);
        textViewDateDetail.setText(tvShow.getFirstAirDate().toString());
        Glide.with(getApplicationContext()).load(ApiClient.IMAGE_URL + tvShow.getBackdropPath()).into(imageViewBackdropDetail);
        Glide.with(getApplicationContext()).load(ApiClient.IMAGE_URL + tvShow.getPosterPath()).into(imageViewPosterDetail);
        ratingBar.setRating((float) tvShow.getVoteAverage() / 2);
    }
}
