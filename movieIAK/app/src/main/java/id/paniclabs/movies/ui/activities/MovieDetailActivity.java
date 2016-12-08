package id.paniclabs.movies.ui.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import id.paniclabs.movies.R;
import id.paniclabs.movies.ui.fragments.MovieDetailFragment;

/**
 * Dibuat oleh Ali pada tanggal 19-11-2016.
 * Nama Project : Movies-IAK2.
 * Email        : panic.inc.dev@gmail.com
 */

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
