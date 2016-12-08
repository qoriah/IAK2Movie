package id.paniclabs.movies.database;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Dibuat oleh Ali pada tanggal 19-11-2016.
 * Nama Project : Movies-IAK2.
 * Email        : panic.inc.dev@gmail.com
 */

public interface FavoriteMovieColumns {
    @DataType(DataType.Type.INTEGER) @PrimaryKey String _ID = "id";
    @DataType(DataType.Type.INTEGER) @NotNull  String MOVIE_ID = "movie_id";
    @DataType(DataType.Type.TEXT) @NotNull String TITLE = "original_title";
    @DataType(DataType.Type.TEXT) @NotNull String OVERVIEW = "overview";
    @DataType(DataType.Type.TEXT) @NotNull String POSTER_PATH = "poster_path";
    @DataType(DataType.Type.TEXT) @NotNull String RELEASE_DATE = "release_date";
    @DataType(DataType.Type.INTEGER) @NotNull String GENRE = "genre";
    @DataType(DataType.Type.TEXT) @NotNull String BACKDROP_PATH = "backdrop_path";
    @DataType(DataType.Type.TEXT) @NotNull String VOTE_COUNT = "vote_count";
    @DataType(DataType.Type.TEXT) @NotNull String VIDEO = "video";
    @DataType(DataType.Type.TEXT) @NotNull String VOTE_AVERAGE = "vote_average";
    @DataType(DataType.Type.TEXT) @NotNull String POPULARITY = "popularity";
    @DataType(DataType.Type.BLOB)  String BACKDROP_IMAGE = "backdrop_image";
    @DataType(DataType.Type.BLOB) String POSTER_IMAGE = "poster_image";
}
