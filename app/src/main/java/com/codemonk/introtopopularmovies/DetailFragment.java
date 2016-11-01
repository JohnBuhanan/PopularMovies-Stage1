package com.codemonk.introtopopularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codemonk.introtopopularmovies.api.tmdb.TMDB;
import com.codemonk.introtopopularmovies.api.tmdb.images.TMDBImages;
import com.codemonk.introtopopularmovies.api.tmdb.rest.models.TMDBModel;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment {

    private static final String LOG_TAG = DetailFragment.class.getSimpleName();

    public DetailFragment() {
        setHasOptionsMenu(true);
    }

    private ImageView mBackdropView;
    private ImageView mPosterView;
    private TextView mTitleView;
    private TextView mVoteAverageView;
    private TextView mOverviewView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        // The detail Activity called via intent.
        Intent intent = getActivity().getIntent();
        if (intent == null || !intent.hasExtra(TMDBModel.class.getSimpleName()))
            return rootView;

        TMDBModel tmdbModel = (TMDBModel) intent.getParcelableExtra(TMDBModel.class.getSimpleName());

        mBackdropView = (ImageView) rootView.findViewById(R.id.detail_backdrop_imageview);
        mPosterView = (ImageView) rootView.findViewById(R.id.detail_poster_imageview);
        mTitleView = (TextView) rootView.findViewById(R.id.detail_title_textview);
        mVoteAverageView = (TextView) rootView.findViewById(R.id.detail_voteaverage_textview);
        mOverviewView = (TextView) rootView.findViewById(R.id.detail_overview_textview);

        Uri backdropUri = TMDB.IMAGES.getBackdropUri(tmdbModel.getBackdropPath());
        Picasso.with(getContext()).load(backdropUri).into((ImageView) mBackdropView);

        Uri posterUri = TMDB.IMAGES.getUri(tmdbModel.getPosterPath(), TMDBImages.TYPE.POSTER_SMALL);
        Picasso.with(getContext()).load(posterUri).into((ImageView) mPosterView);

        String year = tmdbModel.getReleaseDate().split("-")[0];
        String formattedTitle = String.format(tmdbModel.getTitle() + " (%1s)", year);

        mTitleView.setText(formattedTitle);

        Double voteAverage = tmdbModel.getVoteAverage();
        String voteAverageRounded = String.format("%1$.1f", voteAverage);
        String voteAverageText = voteAverageRounded + "/10 ";

        mVoteAverageView.setText(voteAverageText);
        mOverviewView.setText(tmdbModel.getOverview());

        return rootView;
    }
}