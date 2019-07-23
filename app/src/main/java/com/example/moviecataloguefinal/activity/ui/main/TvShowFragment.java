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
import com.example.moviecataloguefinal.adapter.TvShowAdapter;
import com.example.moviecataloguefinal.model.TvShow;
import com.example.moviecataloguefinal.viewmodel.FavoriteTvShowViewModel;
import com.example.moviecataloguefinal.viewmodel.TvShowsViewModel;

import java.util.ArrayList;


public class TvShowFragment extends Fragment {
    private TvShowsViewModel tvShowsViewModel;
    private TvShowAdapter tvShowAdapter;
    private ProgressBar progressBarTvShow;
    private FavoriteTvShowViewModel favoriteTvShowViewModel;


    public TvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);
        progressBarTvShow = view.findViewById(R.id.progressBarTvShow);
        SearchView searchView = view.findViewById(R.id.searchViewTvShow);
        FloatingActionButton floatingActionButtonFavoriteAllTvShow = view.findViewById(R.id.floatingActionButtonFavoriteAllTvShow);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_tvshow);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tvShowAdapter = new TvShowAdapter(getActivity());
        recyclerView.setAdapter(tvShowAdapter);

        tvShowsViewModel = ViewModelProviders.of(getActivity()).get(TvShowsViewModel.class);
        favoriteTvShowViewModel = ViewModelProviders.of(getActivity()).get(FavoriteTvShowViewModel.class);


        String query = "";
        tvShowsViewModel.setListTvShow(query);
        progressBarTvShow.setVisibility(View.VISIBLE);
        tvShowAdapter.notifyDataSetChanged();
        tvShowsViewModel.getListTvShow().observe(getActivity(), getTvShow);

        tvShowAdapter.setOnItemClickListener(new TvShowAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.EXTRA_TV_SHOW, tvShowsViewModel.getListTvShow().getValue().get(position));
                getActivity().startActivity(intent);
            }

            @Override
            public void onFavoriteClick(int position) {
                TvShow searchedTvShow = favoriteTvShowViewModel.getTvShowById(tvShowsViewModel.getListTvShow().getValue().get(position).getId());
                if (searchedTvShow == null) {
                    favoriteTvShowViewModel.insertTvShow(tvShowsViewModel.getListTvShow().getValue().get(position));
                    Toast.makeText(getActivity(), String.format(getActivity().getResources().getString(R.string.message_added_to_database),
                            tvShowsViewModel.getListTvShow().getValue().get(position).getName()), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), String.format(getActivity().getResources().getString(R.string.message_already_in_database),
                            tvShowsViewModel.getListTvShow().getValue().get(position).getName()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onBookmarkClick(int position) {
                Toast.makeText(getActivity(), String.format(getActivity().getResources().getString(R.string.bookmark),
                        tvShowsViewModel.getListTvShow().getValue().get(position).getName()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onShareClick(int position) {
                Toast.makeText(getActivity(), String.format(getActivity().getResources().getString(R.string.share),
                        tvShowsViewModel.getListTvShow().getValue().get(position).getName()), Toast.LENGTH_SHORT).show();
            }
        });

        floatingActionButtonFavoriteAllTvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteTvShowViewModel.insertListTvShow(tvShowsViewModel.getListTvShow().getValue());
                Snackbar.make(v, getActivity().getResources().getString(R.string.message_add_all_tv_show_to_database), Snackbar.LENGTH_SHORT).show();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                tvShowsViewModel.setListTvShow(query);
                progressBarTvShow.setVisibility(View.VISIBLE);
                tvShowAdapter.notifyDataSetChanged();
                tvShowsViewModel.getListTvShow().observe(getActivity(), getTvShow);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                tvShowAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return view;
    }

    private Observer<ArrayList<TvShow>> getTvShow = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(ArrayList<TvShow> tvShows) {
            if (tvShows != null) {
                tvShowAdapter.setListTvShow(tvShows);
                progressBarTvShow.setVisibility(View.INVISIBLE);
            }
        }
    };
}