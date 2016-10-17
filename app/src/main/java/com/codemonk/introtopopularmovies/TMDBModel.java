package com.codemonk.introtopopularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jtbuhana on 10/7/2016.
 */
public class TMDBModel implements Parcelable {
    String movieName;
    String posterPath; // drawable reference id

    public TMDBModel(JSONObject result) throws JSONException {
        this.movieName = result.getString("original_title");
        this.posterPath = result.getString("poster_path");
    }

    public TMDBModel(Parcel in) {
        this.movieName = in.readString();
        this.posterPath = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.movieName);
        parcel.writeString(this.posterPath);
    }

    public final static Parcelable.Creator<TMDBModel> CREATOR = new Parcelable.Creator<TMDBModel>() {
        @Override
        public TMDBModel createFromParcel(Parcel parcel) {
            return new TMDBModel(parcel);
        }

        @Override
        public TMDBModel[] newArray(int i) {
            return new TMDBModel[i];
        }
    };
}