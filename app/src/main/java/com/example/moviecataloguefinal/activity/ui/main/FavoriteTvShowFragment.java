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

import com.example.moviecataloguefinal.R;
import com.example.moviecataloguefinal.activity.DetailMovieActivity;
import com.example.moviecataloguefinal.adapter.FavoriteMovieAdapter;
import com.example.moviecataloguefinal.adapter.FavoriteTvShowAdapter;
import com.example.moviecataloguefinal.model.TvShow;
import com.example.moviecataloguefinal.viewmodel.FavoriteTvShowViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvShowFragment extends Fragment {
    private FavoriteTvShowViewModel favoriteTvShowViewModel;
    private FavoriteTvShowAdapter favoriteTvShowAdapter;
    private ProgressBar progressBar;


    public FavoriteTvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_tv_show, container, false);
        progressBar = view.findViewById(R.id.progressBarFavoriteTvShow);
        FloatingActionButton floatingActionButtonRemoveFavoriteTvShow = view.findViewById(R.id.floatingActionButtonRemoveFavoriteTvshow);
        SearchView searchView = view.findViewById(R.id.searchViewFavoriteTvShow);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_favorite_tvshow);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        favoriteTvShowAdapter = new FavoriteTvShowAdapter(getContext());
        recyclerView.setAdapter(favoriteTvShowAdapter);

        favoriteTvShowViewModel = ViewModelProviders.of(getActivity()).get(FavoriteTvShowViewModel.class);
        favoriteTvShowViewModel.getAllTvShow().observe(this, new Observer<List<TvShow>>() {
            @Override
            public void onChanged(@Nullable List<TvShow> tvShows) {
                progressBar.setVisibility(View.VISIBLE);
                if (tvShows != null) {
                    favoriteTvShowAdapter.setListTvShow(tvShows);
                    progressBar.setVisibility(View.INVISIBLE);

                }
            }
        });

        favoriteTvShowAdapter.setOnItemClickListener(new FavoriteMovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.EXTRA_TV_SHOW, favoriteTvShowViewModel.getAllTvShow().getValue().get(position));
                getActivity().startActivity(intent);
            }

            @Override
            public void onRemoveFavoriteClick(int position) {
                favoriteTvShowViewModel.deleteTvShow(favoriteTvShowViewModel.getAllTvShow().getValue().get(position));
            }
        });

        floatingActionButtonRemoveFavoriteTvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteTvShowViewModel.deleteAllTvShow();
                Snackbar.make(v, getContext().getResources().getString(R.string.message_remove_all_tv_show_from_database), Snackbar.LENGTH_SHORT).show();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                favoriteTvShowAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                favoriteTvShowAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return view;
    }

}
