package com.example.moviecataloguefinal.activity.ui.main;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.moviecataloguefinal.R;
import com.example.moviecataloguefinal.activity.DetailMovieActivity;
import com.example.moviecataloguefinal.adapter.FavoriteMovieAdapter;
import com.example.moviecataloguefinal.model.Movie;
import com.example.moviecataloguefinal.viewmodel.FavoriteMovieViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment {
    private FavoriteMovieViewModel favoriteMovieViewModel;
    private FavoriteMovieAdapter favoriteMovieAdapter;
    private ProgressBar progressBar;


    public FavoriteMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_movie, container, false);
        progressBar = view.findViewById(R.id.progressBarFavoriteMovie);
        FloatingActionButton floatingActionButtonRemoveFavoriteMovies = view.findViewById(R.id.floatingActionButtonRemoveFavoriteMovie);
        SearchView searchView = view.findViewById(R.id.searchViewFavoriteMovie);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_favorite_movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        favoriteMovieAdapter = new FavoriteMovieAdapter(getActivity());
        recyclerView.setAdapter(favoriteMovieAdapter);

        favoriteMovieViewModel = ViewModelProviders.of(getActivity()).get(FavoriteMovieViewModel.class);
        favoriteMovieViewModel.getAllMovie().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                progressBar.setVisibility(View.VISIBLE);
                if (movies != null) {
                    favoriteMovieAdapter.setListMovie(movies);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
        favoriteMovieAdapter.setOnItemClickListener(new FavoriteMovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, favoriteMovieViewModel.getAllMovie().getValue().get(position));
                getActivity().startActivity(intent);
            }

            @Override
            public void onRemoveFavoriteClick(int position) {
                favoriteMovieViewModel.deleteMovie(favoriteMovieViewModel.getAllMovie().getValue().get(position));
                Toast.makeText(getActivity(), String.format(getActivity().getResources().getString(R.string.message_remove_from_database),
                        favoriteMovieViewModel.getAllMovie().getValue().get(position).getTitle()), Toast.LENGTH_SHORT).show();
            }
        });

        floatingActionButtonRemoveFavoriteMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteMovieViewModel.deleteAllMovie();
                Snackbar.make(v, getActivity().getResources().getString(R.string.message_remove_all_favorite_movie_from_database), Snackbar.LENGTH_SHORT).show();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                favoriteMovieAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                favoriteMovieAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return view;
    }

}
