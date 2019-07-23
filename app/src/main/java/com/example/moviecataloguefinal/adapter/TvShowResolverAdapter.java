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
import com.example.moviecataloguefinal.activity.TvShowResolverActivity;
import com.example.moviecataloguefinal.api.ApiClient;
import com.example.moviecataloguefinal.model.TvShow;
import com.example.moviecataloguefinal.provider.MovieProvider;

import java.util.ArrayList;
import java.util.List;

public class TvShowResolverAdapter extends RecyclerView.Adapter<TvShowResolverAdapter.ViewHolder> {
    private Context context;
    private final List<TvShow> listTvShow = new ArrayList<>();

    public TvShowResolverAdapter(Context context) {
        this.context = context;
    }

    public List<TvShow> getListTvShow() {
        return listTvShow;
    }

    public void setListTvShow(List<TvShow> listTvShow) {
        this.listTvShow.clear();
        this.listTvShow.addAll(listTvShow);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_content_resolver, viewGroup, false);
        return new TvShowResolverAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(getListTvShow().get(i));
    }

    @Override
    public int getItemCount() {
        return getListTvShow().size();
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

        void bind(final TvShow tvShow) {
            String description = (tvShow.getOverview().isEmpty()) ? context.getResources().getString(R.string.no_overview) : tvShow.getOverview();
            textViewMovieResolverTitle.setText(tvShow.getName());
            textViewMovieResolverDescription.setText(description);
            Glide.with(context).load(ApiClient.IMAGE_URL + tvShow.getPosterPath()).apply(new RequestOptions().override(350, 550))
                    .into(imgPoster);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailResolverActivity.class);
                    intent.putExtra(DetailResolverActivity.EXTRA_TV_SHOW_RESOLVER, tvShow);
                    context.startActivity(intent);
                }
            });
            buttonRemoveMovieResolver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(MovieProvider.TV_SHOW_URI + "/" + tvShow.getId());
                    context.getContentResolver().delete(uri, null, null);
                    context.getContentResolver().notifyChange(uri, new TvShowResolverActivity.DataObserver(new Handler(), context));
                }
            });
        }
    }
}
