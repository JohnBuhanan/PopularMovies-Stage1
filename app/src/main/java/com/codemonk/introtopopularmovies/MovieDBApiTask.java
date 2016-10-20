package com.codemonk.introtopopularmovies;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.codemonk.introtopopularmovies.api.Repository;
import com.codemonk.introtopopularmovies.api.tmdb.models.TMDBModel;

import java.util.ArrayList;

public class MovieDBApiTask extends AsyncTask<String, Void, ArrayList<TMDBModel>> {

    private final String LOG_TAG = MovieDBApiTask.class.getSimpleName();
    private final ArrayAdapter<TMDBModel> movieDBModelArrayAdapter;

    public MovieDBApiTask(ArrayAdapter<TMDBModel> movieDBModelArrayAdapter) {
        this.movieDBModelArrayAdapter = movieDBModelArrayAdapter;
    }

    @Override
    protected ArrayList<TMDBModel> doInBackground(String... params) {
        if (!Utilities.isOnline())
            return new ArrayList<TMDBModel>();

        String sortType = params[0];

        try {
            if (sortType.equals("top_rated")) {
                return Repository.TMDB.getTopRated().execute().body().getResults();
            } else {
                return Repository.TMDB.getPopular().execute().body().getResults();
            }
        } catch (Exception ex) {
            Log.e(LOG_TAG, "Woops again...");
        }

        return new ArrayList<TMDBModel>();
    }

    @Override
    protected void onPostExecute(ArrayList<TMDBModel> results) {
        if (results != null) {
            movieDBModelArrayAdapter.clear();
            movieDBModelArrayAdapter.addAll(results);
        }
    }
}
