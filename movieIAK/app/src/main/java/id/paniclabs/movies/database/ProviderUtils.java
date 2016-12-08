package id.paniclabs.movies.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import id.paniclabs.movies.api.model.Movie;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Dibuat oleh Ali pada tanggal 19-11-2016.
 * Nama Project : Movies-IAK2.
 * Email        : panic.inc.dev@gmail.com
 */


public class ProviderUtils {
    private static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185";
    private static final String BACKDROP_BASE_URL = "http://image.tmdb.org/t/p/w500";
    private static final String TAG = "PROVIDER_UTILS";

    public static void saveToPopularMovies(Context context, List<Movie> movies) {
        ContentValues contentValues[] = new ContentValues[movies.size()];
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            Bitmap poster = null;
            Bitmap backdrop = null;
            /*try {
                poster = Glide.with(context).load(POSTER_BASE_URL + movie.getPosterPath()).asBitmap().into(-1, -1).get();
                backdrop = Glide.with(context).load(BACKDROP_BASE_URL + movie.getBackdropPath()).asBitmap().into(-1, -1).get();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }*/
            ContentValues values = new ContentValues();
            values.put(PopularMovieColumns.MOVIE_ID, movie.getId());
            values.put(PopularMovieColumns.BACKDROP_PATH, BACKDROP_BASE_URL+movie.getBackdropPath());
            values.put(PopularMovieColumns.POSTER_PATH, POSTER_BASE_URL+movie.getPosterPath());
            values.put(PopularMovieColumns.OVERVIEW, movie.getOverview());
            values.put(PopularMovieColumns.POPULARITY, movie.getPopularity());
            values.put(PopularMovieColumns.GENRE, movie != null ? movie.getGenreIds().get(0) : 1);
            values.put(PopularMovieColumns.RELEASE_DATE, movie.getReleaseDate());
            values.put(PopularMovieColumns.VIDEO, movie.getVideo());
            values.put(PopularMovieColumns.TITLE, movie.getTitle());
            values.put(PopularMovieColumns.VOTE_AVERAGE, movie.getVoteAverage());
            values.put(PopularMovieColumns.VOTE_COUNT, movie.getVoteCount());
            /*values.put(PopularMovieColumns.POSTER_IMAGE, bitmapToString(poster));
            values.put(PopularMovieColumns.BACKDROP_IMAGE, bitmapToString(backdrop));*/
            contentValues[i] = values;
            Log.i(TAG, "saveToPopularMovies: inserted" + i);
        }
        deletePopularMovies(context);
        context.getContentResolver().bulkInsert(MovieProvider.PopularMovies.CONTENT_URI, contentValues);
    }


    public static void saveToTopRatedMovies(Context context, List<Movie> movies) {
        ContentValues contentValues[] = new ContentValues[movies.size()];
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            Bitmap poster = null;
            Bitmap backdrop = null;
            /*try {
                poster = Glide.with(context).load(POSTER_BASE_URL + movie.getPosterPath()).asBitmap().into(-1, -1).get();
                backdrop = Glide.with(context).load(BACKDROP_BASE_URL + movie.getBackdropPath()).asBitmap().into(-1, -1).get();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }*/
            ContentValues values = new ContentValues();
            values.put(TopRatedMovieColumns.MOVIE_ID, movie.getId());
            values.put(TopRatedMovieColumns.BACKDROP_PATH, BACKDROP_BASE_URL+movie.getBackdropPath());
            values.put(TopRatedMovieColumns.POSTER_PATH, POSTER_BASE_URL+movie.getPosterPath());
            values.put(TopRatedMovieColumns.OVERVIEW, movie.getOverview());
            values.put(TopRatedMovieColumns.POPULARITY, movie.getPopularity());
            values.put(TopRatedMovieColumns.GENRE, movie != null ? movie.getGenreIds().get(0) : 1);
            values.put(TopRatedMovieColumns.RELEASE_DATE, movie.getReleaseDate());
            values.put(TopRatedMovieColumns.VIDEO, movie.getVideo());
            values.put(TopRatedMovieColumns.TITLE, movie.getTitle());
            values.put(TopRatedMovieColumns.VOTE_AVERAGE, movie.getVoteAverage());
            values.put(TopRatedMovieColumns.VOTE_COUNT, movie.getVoteCount());
            /*values.put(PopularMovieColumns.POSTER_IMAGE, bitmapToString(poster));
            values.put(PopularMovieColumns.BACKDROP_IMAGE, bitmapToString(backdrop));*/
            contentValues[i] = values;
            Log.i(TAG, "saveToToprated: inserted" + i);
        }
        deleteTopratedMovies(context);
        context.getContentResolver().bulkInsert(MovieProvider.TopRatedMovies.CONTENT_URI, contentValues);
    }

    public static void addToFavorite(Context context, Movie movie) {
        ContentValues values = new ContentValues();
        values.put(FavoriteMovieColumns.MOVIE_ID, movie.getId());
        values.put(FavoriteMovieColumns.BACKDROP_PATH, BACKDROP_BASE_URL+movie.getBackdropPath());
        values.put(FavoriteMovieColumns.POSTER_PATH, POSTER_BASE_URL+movie.getPosterPath());
        values.put(FavoriteMovieColumns.OVERVIEW, movie.getOverview());
        values.put(FavoriteMovieColumns.POPULARITY, movie.getPopularity());
        values.put(FavoriteMovieColumns.GENRE, movie != null ? movie.getGenreIds().get(0) : 1);
        values.put(FavoriteMovieColumns.RELEASE_DATE, movie.getReleaseDate());
        values.put(FavoriteMovieColumns.VIDEO, movie.getVideo());
        values.put(FavoriteMovieColumns.TITLE, movie.getTitle());
        values.put(FavoriteMovieColumns.VOTE_AVERAGE, movie.getVoteAverage());
        values.put(FavoriteMovieColumns.VOTE_COUNT, movie.getVoteCount());
        context.getContentResolver().insert(MovieProvider.FavoriteMovies.CONTENT_URI, values);
    }

    public static void deletePopularMovies(Context context) {
        context.getContentResolver().delete(MovieProvider.PopularMovies.CONTENT_URI, null, null);
    }

    public static void deleteTopratedMovies(Context context) {
        context.getContentResolver().delete(MovieProvider.TopRatedMovies.CONTENT_URI, null, null);
    }

    public static void deleteFavoriteMovie(Context context,Movie movie) {
        context.getContentResolver().delete(MovieProvider.FavoriteMovies.withId(movie.getId()),null,null);
    }

    public static Cursor getFavoriteMovie(Context context, Movie movie) {
        return context.getContentResolver().query(MovieProvider.FavoriteMovies.withId(movie.getId()),null,null,null,null);
    }

    private static String bitmapToString(Bitmap bmp) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, os);
        byte[] bytes = os.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public Bitmap stringToBitmap(String str) {
        byte[] bytesImage = Base64.decode(str, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage.length);
        return bitmap;
    }

    public static void  addToPopularMovies(Context context, List<Movie> results) {
        ContentValues contentValues[] = new ContentValues[results.size()];
        for (int i = 0; i < results.size(); i++) {
            Movie movie = results.get(i);
            Bitmap poster = null;
            Bitmap backdrop = null;

            ContentValues values = new ContentValues();
            values.put(PopularMovieColumns.MOVIE_ID, movie.getId());
            values.put(PopularMovieColumns.BACKDROP_PATH, BACKDROP_BASE_URL+movie.getBackdropPath());
            values.put(PopularMovieColumns.POSTER_PATH, POSTER_BASE_URL+movie.getPosterPath());
            values.put(PopularMovieColumns.OVERVIEW, movie.getOverview());
            values.put(PopularMovieColumns.POPULARITY, movie.getPopularity());
            values.put(PopularMovieColumns.GENRE, movie != null ? movie.getGenreIds().get(0) : 1);
            values.put(PopularMovieColumns.RELEASE_DATE, movie.getReleaseDate());
            values.put(PopularMovieColumns.VIDEO, movie.getVideo());
            values.put(PopularMovieColumns.TITLE, movie.getTitle());
            values.put(PopularMovieColumns.VOTE_AVERAGE, movie.getVoteAverage());
            values.put(PopularMovieColumns.VOTE_COUNT, movie.getVoteCount());
            /*values.put(PopularMovieColumns.POSTER_IMAGE, bitmapToString(poster));
            values.put(PopularMovieColumns.BACKDROP_IMAGE, bitmapToString(backdrop));*/
            contentValues[i] = values;
            Log.i(TAG, "addToPopularMovies: inserted" + i);
        }
        context.getContentResolver().bulkInsert(MovieProvider.PopularMovies.CONTENT_URI, contentValues);
    }

    public static void addtoTopRatedMovies(Context context, List<Movie> results) {
        ContentValues contentValues[] = new ContentValues[results.size()];
        for (int i = 0; i < results.size(); i++) {
            Movie movie = results.get(i);
            Bitmap poster = null;
            Bitmap backdrop = null;
            /*try {
                poster = Glide.with(context).load(POSTER_BASE_URL + movie.getPosterPath()).asBitmap().into(-1, -1).get();
                backdrop = Glide.with(context).load(BACKDROP_BASE_URL + movie.getBackdropPath()).asBitmap().into(-1, -1).get();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }*/
            ContentValues values = new ContentValues();
            values.put(TopRatedMovieColumns.MOVIE_ID, movie.getId());
            values.put(TopRatedMovieColumns.BACKDROP_PATH, BACKDROP_BASE_URL+movie.getBackdropPath());
            values.put(TopRatedMovieColumns.POSTER_PATH, POSTER_BASE_URL+movie.getPosterPath());
            values.put(TopRatedMovieColumns.OVERVIEW, movie.getOverview());
            values.put(TopRatedMovieColumns.POPULARITY, movie.getPopularity());
            values.put(TopRatedMovieColumns.GENRE, movie != null ? movie.getGenreIds().get(0) : 1);
            values.put(TopRatedMovieColumns.RELEASE_DATE, movie.getReleaseDate());
            values.put(TopRatedMovieColumns.VIDEO, movie.getVideo());
            values.put(TopRatedMovieColumns.TITLE, movie.getTitle());
            values.put(TopRatedMovieColumns.VOTE_AVERAGE, movie.getVoteAverage());
            values.put(TopRatedMovieColumns.VOTE_COUNT, movie.getVoteCount());
            /*values.put(PopularMovieColumns.POSTER_IMAGE, bitmapToString(poster));
            values.put(PopularMovieColumns.BACKDROP_IMAGE, bitmapToString(backdrop));*/
            contentValues[i] = values;
            Log.i(TAG, "addToToprated: inserted" + i);
        }
        context.getContentResolver().bulkInsert(MovieProvider.TopRatedMovies.CONTENT_URI, contentValues);
    }

    public static Cursor findMovie(Context context,int id) {
        Cursor cursor;
        if ((cursor = context.getContentResolver().query(MovieProvider.PopularMovies.withId(id), null, null, null, null)).getCount() != 0) {
            return cursor;
        }
        if ((cursor = context.getContentResolver().query(MovieProvider.TopRatedMovies.withId(id), null, null, null, null)) .getCount()!=0) {
            return cursor;
        }
        if ((cursor = context.getContentResolver().query(MovieProvider.FavoriteMovies.withId(id), null, null, null, null)) .getCount()!=0) {
            return cursor;
        } else
            return null;

    }


    public static boolean isFav(Context context, int movieId) {
        Cursor cursor = context.getContentResolver().query(MovieProvider.FavoriteMovies.withId(movieId),
                null,
                null,
                null,
                null);
        if (cursor != null && cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

    public static void addToFav(Context context, int movieId) {
        Cursor cursor = findMovie(context, movieId);
        cursor.moveToFirst();
        ContentValues values = new ContentValues();
        values.put(FavoriteMovieColumns.MOVIE_ID, cursor.getString(cursor.getColumnIndex(PopularMovieColumns.MOVIE_ID)));

        values.put(FavoriteMovieColumns.BACKDROP_PATH, cursor.getString(cursor.getColumnIndex(PopularMovieColumns.BACKDROP_PATH)));

        values.put(FavoriteMovieColumns.POSTER_PATH,cursor.getString(cursor.getColumnIndex(PopularMovieColumns.POSTER_PATH)));
        values.put(FavoriteMovieColumns.OVERVIEW, cursor.getString(cursor.getColumnIndex(PopularMovieColumns.OVERVIEW)));
        values.put(FavoriteMovieColumns.POPULARITY, cursor.getString(cursor.getColumnIndex(PopularMovieColumns.POPULARITY)));
        values.put(FavoriteMovieColumns.GENRE, cursor.getString(cursor.getColumnIndex(PopularMovieColumns.GENRE)));
        values.put(FavoriteMovieColumns.RELEASE_DATE,cursor.getString(cursor.getColumnIndex(PopularMovieColumns.RELEASE_DATE)));
        values.put(FavoriteMovieColumns.VIDEO, cursor.getString(cursor.getColumnIndex(PopularMovieColumns.VIDEO)));
        values.put(FavoriteMovieColumns.TITLE, cursor.getString(cursor.getColumnIndex(PopularMovieColumns.TITLE)));
        values.put(FavoriteMovieColumns.VOTE_AVERAGE, cursor.getString(cursor.getColumnIndex(PopularMovieColumns.VOTE_AVERAGE)));
        values.put(FavoriteMovieColumns.VOTE_COUNT, cursor.getString(cursor.getColumnIndex(PopularMovieColumns.VOTE_COUNT)));
        context.getContentResolver().insert(MovieProvider.FavoriteMovies.CONTENT_URI, values);
            /*values.put(PopularMovieColumns.POSTER_IMAGE, bitmapToString(poster));
            values.put(PopularMovieColumns.BACKDROP_IMAGE, bitmapToString(backdrop));*/
    }

    public static void removeFromFav(Context context, int movieId) {
        context.getContentResolver().delete(MovieProvider.FavoriteMovies.withId(movieId),
                null,
                null);

    }
}
