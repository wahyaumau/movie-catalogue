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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviecataloguefinal.R;
import com.example.moviecataloguefinal.api.ApiClient;
import com.example.moviecataloguefinal.model.TvShow;

import java.util.ArrayList;
import java.util.List;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder> implements Filterable {
    private List<TvShow> listTvShow = new ArrayList<>();
    private List<TvShow> fullListTvShow = new ArrayList<>();
    private Context context;
    private OnItemClickListener onItemClickListener;


    public interface OnItemClickListener {
        void onItemClick(int position);

        void onFavoriteClick(int position);

        void onBookmarkClick(int position);

        void onShareClick(int position);
    }

    public TvShowAdapter(Context context) {
        this.context = context;
    }

    public List<TvShow> getListTvShow() {
        return listTvShow;
    }

    public void setListTvShow(ArrayList<TvShow> listTvShow) {
        this.fullListTvShow.clear();
        this.fullListTvShow.addAll(listTvShow);
        this.listTvShow.clear();
        this.listTvShow.addAll(listTvShow);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tv_show, viewGroup, false);
        return new TvShowViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder tvShowViewHolder, int i) {
        tvShowViewHolder.bind(getListTvShow().get(i));
    }

    @Override
    public int getItemCount() {
        return getListTvShow().size();
    }

    class TvShowViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        ImageButton imageButtonLike, imageButtonBookmark, imageButtonShare;

        TvShowViewHolder(@NonNull View itemView, final OnItemClickListener onItemClickListener) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imageViewTvShowPoster);
            imageButtonLike = itemView.findViewById(R.id.imageButtonLikeTvShow);
            imageButtonBookmark = itemView.findViewById(R.id.imageButtonBookmarkTvShow);
            imageButtonShare = itemView.findViewById(R.id.imageButtonShareTvShow);

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

            imageButtonLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onFavoriteClick(position);
                        }
                    }
                }
            });

            imageButtonBookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onBookmarkClick(position);
                        }
                    }
                }
            });

            imageButtonShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onShareClick(position);
                        }
                    }
                }
            });

        }

        void bind(final TvShow tvShow) {
            Glide.with(context).load(ApiClient.IMAGE_URL + tvShow.getPosterPath()).apply(new RequestOptions().override(350, 550))
                    .into(imgPoster);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<TvShow> filteredList = new ArrayList<>();
                if (constraint.toString().isEmpty()) {
                    filteredList = fullListTvShow;
                } else {
                    for (TvShow tvShow : fullListTvShow) {
                        if (tvShow.getOriginalName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            filteredList.add(tvShow);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listTvShow = (List<TvShow>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
