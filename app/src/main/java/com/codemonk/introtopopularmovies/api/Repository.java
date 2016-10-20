package com.codemonk.introtopopularmovies.api;

import com.codemonk.introtopopularmovies.api.tmdb.TMDBApi;

/**
 * Created by jtbuhana on 10/18/2016.
 */

public final class Repository {
    public static final TMDBApi TMDB = TMDBApi.RETROFIT.create(TMDBApi.class);
}
