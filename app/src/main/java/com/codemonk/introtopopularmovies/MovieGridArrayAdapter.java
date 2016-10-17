package com.codemonk.introtopopularmovies;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieGridArrayAdapter extends ArrayAdapter<TMDBModel> {
    private static final String LOG_TAG = MovieGridArrayAdapter.class.getSimpleName();

    public MovieGridArrayAdapter(Activity activity, ArrayList<TMDBModel> TMDBModels) {
        super(activity, 0, TMDBModels);
    }

    final String BASE_TMDB_IMAGE_URL = "http://image.tmdb.org/t/p/w185";

    // http://image.tmdb.org/t/p/w185/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg
    // http://image.tmdb.org/t/p/w185
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        TMDBModel TMDBModel = getItem(position);

        Context context = getContext();

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.grid_item_movie, viewGroup, false);
        }

        Uri uri = Uri.parse(BASE_TMDB_IMAGE_URL)
                .buildUpon()
                .appendPath(TMDBModel.posterPath.replace("/", ""))
                .build();

        Picasso.with(context).load(uri).into((ImageView) view);

        return view;
    }
}
