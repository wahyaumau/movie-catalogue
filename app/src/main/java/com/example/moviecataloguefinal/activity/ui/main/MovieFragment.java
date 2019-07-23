package com.example.moviecataloguefinal.activity.ui.main;


import android.arch.lifecycle.LifecycleOwner;
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
import com.example.moviecataloguefinal.adapter.MovieAdapter;
import com.example.moviecataloguefinal.model.Movie;
import com.example.moviecataloguefinal.viewmodel.FavoriteMovieViewModel;
import com.example.moviecataloguefinal.viewmodel.MoviesViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    private MoviesViewModel movieViewModel;
    private FavoriteMovieViewModel favoriteMovieViewModel;
    private MovieAdapter movieAdapter;
    private ProgressBar progressBar;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        progressBar = view.findViewById(R.id.progressBarMovie);
        SearchView searchView = view.findViewById(R.id.searchViewMovies);
        FloatingActionButton floatingActionButtonFavoriteAllMovie = view.findViewById(R.id.floatingActionButtonFavoriteMovieAll);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        movieAdapter = new MovieAdapter(getActivity());
        recyclerView.setAdapter(movieAdapter);

        movieViewModel = ViewModelProviders.of(getActivity()).get(MoviesViewModel.class);
        favoriteMovieViewModel = ViewModelProviders.of(getActivity()).get(FavoriteMovieViewModel.class);
        String query = "";
        movieViewModel.setListMovie(query);
        progressBar.setVisibility(View.VISIBLE);
        movieAdapter.notifyDataSetChanged();
        movieViewModel.getListMovie().observe(getActivity(), getMovies);

        movieAdapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieViewModel.getListMovie().getValue().get(position));
                getActivity().startActivity(intent);
            }

            @Override
            public void onAddFavoriteClick(int position) {
                Movie searchedMovie = favoriteMovieViewModel.getMovieById(movieViewModel.getListMovie().getValue().get(position).getId());
                if (searchedMovie == null) {
                    favoriteMovieViewModel.insertMovie(movieViewModel.getListMovie().getValue().get(position));
                    Toast.makeText(getActivity(), String.format(getActivity().getResources().getString(R.string.message_added_to_database),
                            movieViewModel.getListMovie().getValue().get(position).getTitle()), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), String.format(getActivity().getResources().getString(R.string.message_already_in_database),
                            movieViewModel.getListMovie().getValue().get(position).getTitle()), Toast.LENGTH_SHORT).show();
                }
            }
        });

        floatingActionButtonFavoriteAllMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteMovieViewModel.insertListMovie(movieViewModel.getListMovie().getValue());
                Snackbar.make(v, getActivity().getResources().getText(R.string.message_add_all_movie_to_database), Snackbar.LENGTH_SHORT).show();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieViewModel.setListMovie(query);
                progressBar.setVisibility(View.VISIBLE);
                movieAdapter.notifyDataSetChanged();
                movieViewModel.getListMovie().observe(getActivity(), getMovies);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                movieAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return view;

    }

    private Observer<ArrayList<Movie>> getMovies = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if (movies != null) {
                movieAdapter.setListMovie(movies);
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    };
}