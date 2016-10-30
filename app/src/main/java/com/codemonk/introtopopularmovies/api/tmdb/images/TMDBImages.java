package com.codemonk.introtopopularmovies.api.tmdb.images;

import android.net.Uri;

/**
 * Created by jtbuhana on 10/22/2016.
 */

// We don't need an API key to fetch images from TMDB.
// "w92", "w154", "w185","w342", "w500", "w780", or "original". For most phones we recommend using “w185”.
public final class TMDBImages {
    private static final Uri BASE_URI = Uri.parse("http://image.tmdb.org/t/p/");

    public enum SIZE {
        W92,
        W154,
        W185,
        W342,
        W500,
        W780,
        ORIGINAL;

        public String path() {
            return this.name().toLowerCase();
        }
    }

    public enum TYPE {
        BACKDROP(SIZE.W342),
        POSTER(SIZE.W154),
        POSTER_SMALL(SIZE.W92);

        private SIZE _size;

        TYPE(SIZE size) {
            this._size = size;
        }

        public String path() {
            return this._size.path();
        }
    }

    public TMDBImages() {
    }

    public Uri getPosterUri(String imagePath) {
        return getUri(imagePath, TYPE.POSTER);
    }

    public Uri getBackdropUri(String imagePath) {
        return getUri(imagePath, TYPE.BACKDROP);
    }

    public Uri getUri(String imagePath, TYPE type) {
        String sizePath = type.path();
        return getUri(imagePath, sizePath);
    }

    private Uri getUri(String imagePath, SIZE size) {
        String sizePath = size.path();
        return getUri(imagePath, sizePath);
    }

    private Uri getUri(String imagePath, String sizePath) {

        String strippedImagePath = imagePath.replace("/", "");

        Uri uri = BASE_URI.buildUpon()
                .appendPath(sizePath)
                .appendPath(strippedImagePath)
                .build();

        return uri;
    }
}
