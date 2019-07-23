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

@Entity(tableName = Movie.TABLE_NAME)
public class Movie implements Parcelable {
    public static final String TABLE_NAME = "movies";
    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_VOTE_COUNT = "vote_count";
    public static final String COLUMN_VIDEO = "video";
    public static final String COLUMN_VOTE_AVERAGE = "vote_average";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_POPULARITY = "popularity";
    public static final String COLUMN_POSTER_PATH = "poster_path";
    public static final String COLUMN_ORIGINAL_LANGUAGE = "original_language";
    public static final String COLUMN_ORIGINAL_TITLE = "original_title";
    public static final String COLUMN_GENRE_IDS = "genre_ids";
    public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
    public static final String COLUMN_ADULT = "adult";
    public static final String COLUMN_OVERVIEW = "overview";
    public static final String COLUMN_RELEASE_DATE = "release_date";


    @PrimaryKey()
    @ColumnInfo(index = true, name = COLUMN_ID)
    private long id;
    @SerializedName("vote_count")
    @ColumnInfo(name = COLUMN_VOTE_COUNT)
    private long voteCount;
    @ColumnInfo(name = COLUMN_VIDEO)
    private boolean video;
    @SerializedName("vote_average")
    @ColumnInfo(name = COLUMN_VOTE_AVERAGE)
    private double voteAverage;
    @ColumnInfo(name = COLUMN_TITLE)
    private String title;
    @ColumnInfo(name = COLUMN_POPULARITY)
    private double popularity;
    @SerializedName("poster_path")
    @ColumnInfo(name = COLUMN_POSTER_PATH)
    private String posterPath;
    @SerializedName("original_language")
    @ColumnInfo(name = COLUMN_ORIGINAL_LANGUAGE)
    private String originalLanguage;
    @SerializedName("original_title")
    @ColumnInfo(name = COLUMN_ORIGINAL_TITLE)
    private String originalTitle;
    @SerializedName("genre_ids")
    @ColumnInfo(name = COLUMN_GENRE_IDS)
    private ArrayList<String> genreIds;
    @SerializedName("backdrop_path")
    @ColumnInfo(name = COLUMN_BACKDROP_PATH)
    private String backdropPath;
    @ColumnInfo(name = COLUMN_ADULT)
    private boolean adult;
    @ColumnInfo(name = COLUMN_OVERVIEW)
    private String overview;
    @SerializedName("release_date")
    @ColumnInfo(name = COLUMN_RELEASE_DATE)
    private Date releaseDate;

    public static Movie fromContentValues(ContentValues contentValues) {
        Movie movie = new Movie();
        if (contentValues.containsKey(COLUMN_ID)) {
            movie.id = contentValues.getAsLong(COLUMN_ID);
        }
        if (contentValues.containsKey(COLUMN_VOTE_COUNT)) {
            movie.voteCount = contentValues.getAsLong(COLUMN_VOTE_COUNT);
        }
        if (contentValues.containsKey(COLUMN_VIDEO)) {
            movie.video = contentValues.getAsBoolean(COLUMN_VIDEO);
        }
        if (contentValues.containsKey(COLUMN_VOTE_AVERAGE)) {
            movie.voteAverage = contentValues.getAsDouble(COLUMN_VOTE_AVERAGE);
        }
        if (contentValues.containsKey(COLUMN_TITLE)) {
            movie.title = contentValues.getAsString(COLUMN_TITLE);
        }
        if (contentValues.containsKey(COLUMN_POPULARITY)) {
            movie.popularity = contentValues.getAsDouble(COLUMN_POPULARITY);
        }
        if (contentValues.containsKey(COLUMN_POSTER_PATH)) {
            movie.posterPath = contentValues.getAsString(COLUMN_POSTER_PATH);
        }
        if (contentValues.containsKey(COLUMN_ORIGINAL_LANGUAGE)) {
            movie.originalLanguage = contentValues.getAsString(COLUMN_ORIGINAL_LANGUAGE);
        }
        if (contentValues.containsKey(COLUMN_ORIGINAL_TITLE)) {
            movie.originalTitle = contentValues.getAsString(COLUMN_ORIGINAL_TITLE);
        }
        if (contentValues.containsKey(COLUMN_GENRE_IDS)) {
            String ids = contentValues.getAsString(COLUMN_GENRE_IDS);
            movie.genreIds = new ArrayList<>(Arrays.asList(ids.split(",")));
        }
        if (contentValues.containsKey(COLUMN_BACKDROP_PATH)) {
            movie.backdropPath = contentValues.getAsString(COLUMN_BACKDROP_PATH);
        }
        if (contentValues.containsKey(COLUMN_ADULT)) {
            movie.adult = contentValues.getAsBoolean(COLUMN_ADULT);
        }
        if (contentValues.containsKey(COLUMN_OVERVIEW)) {
            movie.overview = contentValues.getAsString(COLUMN_OVERVIEW);
        }
        if (contentValues.containsKey(COLUMN_RELEASE_DATE)) {
            try {
                movie.releaseDate = new SimpleDateFormat("yyyy-mm-dd").
                        parse(contentValues.getAsString(COLUMN_RELEASE_DATE));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return movie;
    }

    @Ignore
    public Movie() {
    }

    public Movie(long id, long voteCount, boolean video, double voteAverage, String title, double popularity, String posterPath, String originalLanguage, String originalTitle, ArrayList<String> genreIds, String backdropPath, boolean adult, String overview, Date releaseDate) {
        this.id = id;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
        this.title = title;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.genreIds = genreIds;
        this.backdropPath = backdropPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(long voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public ArrayList<String> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(ArrayList<String> genreIds) {
        this.genreIds = genreIds;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.voteCount);
        dest.writeByte(this.video ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.voteAverage);
        dest.writeString(this.title);
        dest.writeDouble(this.popularity);
        dest.writeString(this.posterPath);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.originalTitle);
        dest.writeStringList(this.genreIds);
        dest.writeString(this.backdropPath);
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
        dest.writeString(this.overview);
        dest.writeLong(this.releaseDate != null ? this.releaseDate.getTime() : -1);
    }

    protected Movie(Parcel in) {
        this.id = in.readLong();
        this.voteCount = in.readLong();
        this.video = in.readByte() != 0;
        this.voteAverage = in.readDouble();
        this.title = in.readString();
        this.popularity = in.readDouble();
        this.posterPath = in.readString();
        this.originalLanguage = in.readString();
        this.originalTitle = in.readString();
        this.genreIds = in.createStringArrayList();
        this.backdropPath = in.readString();
        this.adult = in.readByte() != 0;
        this.overview = in.readString();
        long tmpReleaseDate = in.readLong();
        this.releaseDate = tmpReleaseDate == -1 ? null : new Date(tmpReleaseDate);
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
