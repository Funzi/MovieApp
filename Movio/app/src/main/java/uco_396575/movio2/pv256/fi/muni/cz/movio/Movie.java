package uco_396575.movio2.pv256.fi.muni.cz.movio;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private long realeaseDate;
    private String coverPath;
    private String title;
    private String backDrop;
    private float popularity;


    public Movie() {
    }

    public Movie(String title) {
        this.title = title;
    }

    public Movie(long realeaseDate, String coverPath, String title, String backDrop, float popularity) {

        this.realeaseDate = realeaseDate;
        this.coverPath = coverPath;
        this.title = title;
        this.backDrop = backDrop;
        this.popularity = popularity;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public long getRealeaseDate() {
        return realeaseDate;
    }

    public void setRealeaseDate(long realeaseDate) {
        this.realeaseDate = realeaseDate;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackDrop() {
        return backDrop;
    }

    public void setBackDrop(String backDrop) {
        this.backDrop = backDrop;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    protected Movie(Parcel in) {
        realeaseDate = in.readLong();
        coverPath = in.readString();
        title = in.readString();
        backDrop = in.readString();
        popularity = in.readFloat();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(realeaseDate);
        parcel.writeString(coverPath);
        parcel.writeString(title);
        parcel.writeString(backDrop);
        parcel.writeFloat(popularity);
    }
}
