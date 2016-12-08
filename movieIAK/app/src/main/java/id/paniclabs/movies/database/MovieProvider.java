package id.paniclabs.movies.database;

import android.content.ContentUris;
import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Dibuat oleh Ali pada tanggal 19-11-2016.
 * Nama Project : Movies-IAK2.
 * Email        : panic.inc.dev@gmail.com
 */

@ContentProvider(authority = MovieProvider.AUTHORITY, database = MovieDatabase.class)
public class MovieProvider {

    public static final String AUTHORITY = "id.paniclabs.movies.database.MovieProvider";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

    //Constant for each path
    interface Path {
        String POPULAR = "popular";
        String TOPRATED = "toprated";
        String FAVORITE = "favorite";
        String VIDEOS = "videos";
        String REVIEWS = "reviews";
    }


    //Build uri for each path
    private static Uri buildUri(String... path) {
        Uri.Builder builder = BASE_URI.buildUpon();
        for (String s :
                path) {
            builder.appendPath(s);
        }
        return builder.build();
    }

    //End point for Popular movies Table
    @TableEndpoint(table = MovieDatabase.POPULAR_MOVIE)
    public static final class PopularMovies {

        @ContentUri(
                path = Path.POPULAR,
                type = "vnd.android.cursor.dir/movies")
        public static final Uri CONTENT_URI = buildUri(Path.POPULAR);

        @InexactContentUri(
                path =Path.POPULAR+"/#",
                name="POPULARMOVIE_ID",
                type = "vnd.android.cursor.item/movie",
                whereColumn = PopularMovieColumns.MOVIE_ID,
                pathSegment = 1)
        public static final Uri withId(int id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    //End point for Top Rated Movies
    @TableEndpoint(table = MovieDatabase.TOP_RATED)
    public static final class TopRatedMovies {

        @ContentUri(
                path = Path.TOPRATED,
                type = "vnd.android.cursor.dir/movies")
        public static final Uri CONTENT_URI = buildUri(Path.TOPRATED);

        @InexactContentUri(
                path =Path.TOPRATED+"/#",
                name="TOPRATEDMOVIE_ID",
                type = "vnd.android.cursor.item/movie",
                whereColumn = TopRatedMovieColumns.MOVIE_ID,
                pathSegment = 1)
        public static final Uri withId(int id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    //End point for Favorite Movie Column
    @TableEndpoint(table = MovieDatabase.FAVORITE)
    public static final class FavoriteMovies {

        @ContentUri(
                path = Path.FAVORITE,
                type = "vnd.android.cursor.dir/movies")
        public static final Uri CONTENT_URI = buildUri(Path.FAVORITE);

        @InexactContentUri(
                path =Path.FAVORITE+"/#",
                name="FAVORITEMOVIE_ID",
                type = "vnd.android.cursor.item/movie",
                whereColumn = FavoriteMovieColumns.MOVIE_ID,
                pathSegment = 1)
        public static final Uri withId(int id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }


    }


}
