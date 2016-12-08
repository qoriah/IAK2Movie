package id.paniclabs.movies.api;


import android.content.Context;

import id.paniclabs.movies.database.MovieProvider;
import id.paniclabs.movies.database.ProviderUtils;
import id.paniclabs.movies.api.response.MovieResponse;
import id.paniclabs.movies.api.response.ReviewResponse;
import id.paniclabs.movies.api.response.VideoResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Dibuat oleh Ali pada tanggal 19-11-2016.
 * Nama Project : Movies-IAK2.
 * Email        : panic.inc.dev@gmail.com
 */

public class ApiServices {
    private static Retrofit sRetrofit;
    public static final String API_KEY = "76cabe3b54f74ab4c0934c6fcfd4bd90";
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String RESPONSE_POPULAR = "popular";
    public static final String RESPONSE_TOP_RATED = "top_rated";

    private ApiServices() {

    }

    public static Retrofit getRetrofit() {
        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return sRetrofit;
    }

    public static interface Fetch {
        @GET("movie/{resType}?api_key=76cabe3b54f74ab4c0934c6fcfd4bd90")
        public Call<MovieResponse> fetchMovies(@Path("resType") String type, @Query("page") String pageno);

        @GET("movie/{movie}/videos?api_key=76cabe3b54f74ab4c0934c6fcfd4bd90")
        public Call<VideoResponse> fetchVideos(@Path("movie") String movie);

        @GET("movie/{movie}/reviews?api_key=76cabe3b54f74ab4c0934c6fcfd4bd90")
        public Call<ReviewResponse> fetchReviews(@Path("movie") String movie);
    }


    public static void fetchPopularMovies(final Context context, String pageno) {
        Retrofit retrofit = ApiServices.getRetrofit();
        ApiServices.Fetch fetch = retrofit.create(ApiServices.Fetch.class);
        Call<MovieResponse> call = fetch.fetchMovies(ApiServices.RESPONSE_POPULAR, pageno);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                if (context.getContentResolver().query(MovieProvider.PopularMovies.CONTENT_URI, null, null, null, null, null).getCount() > 0) {
                    ProviderUtils.addToPopularMovies(context, response.body().getResults());
                } else {
                    ProviderUtils.saveToPopularMovies(context, response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

    }

    public static void fetchTopRatedMovies(final Context context, String pageno) {
        Retrofit retrofit = ApiServices.getRetrofit();
        ApiServices.Fetch fetch = retrofit.create(ApiServices.Fetch.class);
        Call<MovieResponse> call = fetch.fetchMovies(ApiServices.RESPONSE_TOP_RATED, pageno);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (context.getContentResolver().query(MovieProvider.PopularMovies.CONTENT_URI, null, null, null, null, null).getCount() > 0) {
                    ProviderUtils.addtoTopRatedMovies(context, response.body().getResults());

                } else {
                    ProviderUtils.saveToTopRatedMovies(context, response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }



}
