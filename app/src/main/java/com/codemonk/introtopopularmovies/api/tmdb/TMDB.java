package com.codemonk.introtopopularmovies.api.tmdb;

import com.codemonk.introtopopularmovies.api.tmdb.images.TMDBImages;
import com.codemonk.introtopopularmovies.api.tmdb.rest.TMDBApi;

/**
 * Created by jtbuhana on 10/18/2016.
 */

public final class TMDB {
    public static final TMDBApi API = TMDBApi.RETROFIT.create(TMDBApi.class);
    public static final TMDBImages IMAGES = new TMDBImages();
}