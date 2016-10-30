package com.codemonk.introtopopularmovies.api.tmdb.rest;

import com.codemonk.introtopopularmovies.BuildConfig;
import com.codemonk.introtopopularmovies.api.tmdb.rest.models.TMDBModel;
import com.codemonk.introtopopularmovies.api.tmdb.rest.models.TMDBResponse;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface TMDBApi {
    @GET("movie/popular")
    Call<TMDBResponse> getPopular();

    @GET("movie/top_rated")
    Call<TMDBResponse> getTopRated();

    @GET("movie/{id}")
    Call<TMDBModel> getMovieDetails(@Path("id") String id);

    Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            HttpUrl httpUrl = originalRequest.url();

            HttpUrl.Builder httpUrlBuilder = httpUrl
                    .newBuilder()
                    .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY);

            // Request customization: add request headers
            Request.Builder requestBuilder = originalRequest
                    .newBuilder()
                    .url(httpUrlBuilder.build())
                    .method(originalRequest.method(), originalRequest.body());

            Request newRequest = requestBuilder.build();

            return chain.proceed(newRequest);
        }
    };

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build();

    String BASE_URL = "https://api.themoviedb.org/3/";

    public static final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}