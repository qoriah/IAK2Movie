package id.paniclabs.movies.database;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Dibuat oleh Ali pada tanggal 19-11-2016.
 * Nama Project : Movies-IAK2.
 * Email        : panic.inc.dev@gmail.com
 */

@Database(version = MovieDatabase.VERSION )
public final class MovieDatabase {
    //database version
    public static final int VERSION = 1;

    //Popular Movies Table
    @Table(PopularMovieColumns.class)public static final String POPULAR_MOVIE ="popular";

    //TopRated Movies Table
    @Table(TopRatedMovieColumns.class) public static final String TOP_RATED = "top_rated";

    //FavoriteMovie Table
    @Table(FavoriteMovieColumns.class)public static final String FAVORITE ="favorite";



}

