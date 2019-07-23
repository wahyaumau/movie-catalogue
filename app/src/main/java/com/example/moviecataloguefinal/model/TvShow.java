package com.example.moviecataloguefinal.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@Entity(tableName = "tv_shows")
public class TvShow implements Parcelable {
    public static final String TABLE_NAME = "tv_shows";
    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_ORIGINAL_NAME = "original_name";
    public static final String COLUMN_GENRE_IDS = "genre_ids";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_POPULARITY = "popularity";
    public static final String COLUMN_ORIGIN_COUNTRY = "origin_country";
    public static final String COLUMN_VOTE_COUNT = "vote_count";
    public static final String COLUMN_FIRST_AIR_DATE = "first_air_date";
    public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
    public static final String COLUMN_ORIGINAL_LANGUAGE = "original_language";
    public static final String COLUMN_VOTE_AVERAGE = "vote_average";
    public static final String COLUMN_OVERVIEW = "overview";
    public static final String COLUMN_POSTER_PATH = "poster_path";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    private long id;
    @SerializedName("original_name")
    @ColumnInfo(name = COLUMN_ORIGINAL_NAME)
    private String originalName;
    @SerializedName("genre_ids")
    @ColumnInfo(name = COLUMN_GENRE_IDS)
    private ArrayList<String> genreIds;
    @ColumnInfo(name = COLUMN_NAME)
    private String name;
    @ColumnInfo(name = COLUMN_POPULARITY)
    private double popularity;
    @SerializedName("origin_country")
    @ColumnInfo(name = COLUMN_ORIGIN_COUNTRY)
    private ArrayList<String> originCountry;
    @SerializedName("vote_count")
    @ColumnInfo(name = COLUMN_VOTE_COUNT)
    private long voteCount;
    @SerializedName("first_air_date")
    @ColumnInfo(name = COLUMN_FIRST_AIR_DATE)
    private Date firstAirDate;
    @SerializedName("backdrop_path")
    @ColumnInfo(name = COLUMN_BACKDROP_PATH)
    private String backdropPath;
    @SerializedName("original_language")
    @ColumnInfo(name = COLUMN_ORIGINAL_LANGUAGE)
    private String originalLanguage;
    @SerializedName("vote_average")
    @ColumnInfo(name = COLUMN_VOTE_AVERAGE)
    private double voteAverage;
    @ColumnInfo(name = COLUMN_OVERVIEW)
    private String overview;
    @SerializedName("poster_path")
    @ColumnInfo(name = COLUMN_POSTER_PATH)
    private String posterPath;

    public static TvShow fromContentValues(ContentValues contentValues) {
        TvShow tvShow = new TvShow();
        if (contentValues.containsKey(COLUMN_ID)) {
            tvShow.id = contentValues.getAsLong(COLUMN_ID);
        }
        if (contentValues.containsKey(COLUMN_VOTE_COUNT)) {
            tvShow.voteCount = contentValues.getAsLong(COLUMN_VOTE_COUNT);
        }
        if (contentValues.containsKey(COLUMN_NAME)) {
            tvShow.name = contentValues.getAsString(COLUMN_NAME);
        }
        if (contentValues.containsKey(COLUMN_VOTE_AVERAGE)) {
            tvShow.voteAverage = contentValues.getAsDouble(COLUMN_VOTE_AVERAGE);
        }
        if (contentValues.containsKey(COLUMN_POPULARITY)) {
            tvShow.popularity = contentValues.getAsDouble(COLUMN_POPULARITY);
        }
        if (contentValues.containsKey(COLUMN_POSTER_PATH)) {
            tvShow.posterPath = contentValues.getAsString(COLUMN_POSTER_PATH);
        }
        if (contentValues.containsKey(COLUMN_ORIGINAL_LANGUAGE)) {
            tvShow.originalLanguage = contentValues.getAsString(COLUMN_ORIGINAL_LANGUAGE);
        }
        if (contentValues.containsKey(COLUMN_ORIGINAL_NAME)) {
            tvShow.name = contentValues.getAsString(COLUMN_ORIGINAL_NAME);
        }
        if (contentValues.containsKey(COLUMN_GENRE_IDS)) {
            String ids = contentValues.getAsString(COLUMN_GENRE_IDS);
            tvShow.genreIds = new ArrayList<>(Arrays.asList(ids.split(",")));
        }
        if (contentValues.containsKey(COLUMN_BACKDROP_PATH)) {
            tvShow.backdropPath = contentValues.getAsString(COLUMN_BACKDROP_PATH);
        }
        if (contentValues.containsKey(COLUMN_ORIGIN_COUNTRY)) {
            String originalCountries = contentValues.getAsString(COLUMN_ORIGIN_COUNTRY);
            tvShow.originCountry = new ArrayList<>(Arrays.asList(originalCountries.split(",")));

        }
        if (contentValues.containsKey(COLUMN_OVERVIEW)) {
            tvShow.overview = contentValues.getAsString(COLUMN_OVERVIEW);
        }
        if (contentValues.containsKey(COLUMN_FIRST_AIR_DATE)) {
            try {
                tvShow.firstAirDate = new SimpleDateFormat("yyyy-mm-dd").
                        parse(contentValues.getAsString(COLUMN_FIRST_AIR_DATE));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return tvShow;
    }

    @Ignore
    public TvShow() {
    }

    public TvShow(long id, String originalName, ArrayList<String> genreIds, String name, double popularity, ArrayList<String> originCountry, long voteCount, Date firstAirDate, String backdropPath, String originalLanguage, double voteAverage, String overview, String posterPath) {
        this.id = id;
        this.originalName = originalName;
        this.genreIds = genreIds;
        this.name = name;
        this.popularity = popularity;
        this.originCountry = originCountry;
        this.voteCount = voteCount;
        this.firstAirDate = firstAirDate;
        this.backdropPath = backdropPath;
        this.originalLanguage = originalLanguage;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.posterPath = posterPath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public ArrayList<String> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(ArrayList<String> genreIds) {
        this.genreIds = genreIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public ArrayList<String> getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(ArrayList<String> originCountry) {
        this.originCountry = originCountry;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(long voteCount) {
        this.voteCount = voteCount;
    }

    public Date getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(Date firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.originalName);
        dest.writeStringList(this.genreIds);
        dest.writeString(this.name);
        dest.writeDouble(this.popularity);
        dest.writeStringList(this.originCountry);
        dest.writeLong(this.voteCount);
        dest.writeLong(this.firstAirDate != null ? this.firstAirDate.getTime() : -1);
        dest.writeString(this.backdropPath);
        dest.writeString(this.originalLanguage);
        dest.writeDouble(this.voteAverage);
        dest.writeString(this.overview);
        dest.writeString(this.posterPath);
    }

    protected TvShow(Parcel in) {
        this.id = in.readLong();
        this.originalName = in.readString();
        this.genreIds = in.createStringArrayList();
        this.name = in.readString();
        this.popularity = in.readDouble();
        this.originCountry = in.createStringArrayList();
        this.voteCount = in.readLong();
        long tmpFirstAirDate = in.readLong();
        this.firstAirDate = tmpFirstAirDate == -1 ? null : new Date(tmpFirstAirDate);
        this.backdropPath = in.readString();
        this.originalLanguage = in.readString();
        this.voteAverage = in.readDouble();
        this.overview = in.readString();
        this.posterPath = in.readString();
    }

    public static final Creator<TvShow> CREATOR = new Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel source) {
            return new TvShow(source);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };
}
