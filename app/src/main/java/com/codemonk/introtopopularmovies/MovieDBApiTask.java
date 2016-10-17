package com.codemonk.introtopopularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MovieDBApiTask extends AsyncTask<String, Void, ArrayList<TMDBModel>> {

    private final String LOG_TAG = MovieDBApiTask.class.getSimpleName();
    private final ArrayAdapter<TMDBModel> movieDBModelArrayAdapter;

    public MovieDBApiTask(ArrayAdapter<TMDBModel> movieDBModelArrayAdapter) {
        this.movieDBModelArrayAdapter = movieDBModelArrayAdapter;
    }

    final String BASE_URL = "https://api.themoviedb.org/3/movie";

    // http://api.themoviedb.org/3/movie/popular?api_key=[THE_MOVIE_DB_API_KEY]
    private URL getURL(String sortType) {
        Uri uri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath(sortType)
                .appendQueryParameter("api_key", BuildConfig.THE_MOVIE_DB_API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Malformed URL", e);
        }

        return url;
    }

    private String callMovieDB(String sortType) {
        URL url = getURL(sortType);

        String jsonResponse = URLHelper.Get(url);

        return jsonResponse;
    }

    private ArrayList<TMDBModel> parseJsonData(String json) {
        ArrayList<TMDBModel> TMDBModels = new ArrayList<TMDBModel>();
        // These are the names of the JSON objects that need to be extracted.
        // http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg
        try {
            JSONObject root = new JSONObject(json);

            JSONArray results = (JSONArray) root.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                TMDBModels.add(new TMDBModel(result));
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "JSON Parsing exception.", e);
        }

        return TMDBModels;
    }

    @Override
    protected ArrayList<TMDBModel> doInBackground(String... params) {
        if (!URLHelper.isOnline())
            return new ArrayList<TMDBModel>();

        String sortType = params[0];

        String json = callMovieDB(sortType);

        ArrayList<TMDBModel> parsedTMDBModels = parseJsonData(json);

        return parsedTMDBModels;
    }

    @Override
    protected void onPostExecute(ArrayList<TMDBModel> results) {
        if (results != null) {
            movieDBModelArrayAdapter.clear();
            movieDBModelArrayAdapter.addAll(results);
        }
    }
}
