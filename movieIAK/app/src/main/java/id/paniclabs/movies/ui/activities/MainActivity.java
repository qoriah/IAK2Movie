package id.paniclabs.movies.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.paniclabs.movies.R;
import id.paniclabs.movies.Utility.Prefs;
import id.paniclabs.movies.ui.adapters.MovieListAdapter;
import id.paniclabs.movies.ui.fragments.FavoriteMovieFragment;
import id.paniclabs.movies.ui.fragments.MovieDetailFragment;
import id.paniclabs.movies.ui.fragments.PopularMovieFragment;
import id.paniclabs.movies.ui.fragments.TopRatedMovieFragment;

/**
 * Dibuat oleh Ali pada tanggal 19-11-2016.
 * Nama Project : Movies-IAK2.
 * Email        : panic.inc.dev@gmail.com
 */

public class MainActivity extends AppCompatActivity implements
        PopularMovieFragment.OnPopularMovieFragmentInteractionListener,
        TopRatedMovieFragment.OnTopRatedMovieFragmentInteractionListener,
        FavoriteMovieFragment.OnFavoriteMovieFragmentInteractionListener,
        MovieDetailFragment.OnFragmentInteractionListener,
        MovieListAdapter.AdapterCallback {


    private boolean mTwoPane = false;
    private FragmentManager mFragmentManager;
    @BindView(R.id.bottombar) BottomBar mBottomBar;
    @BindView(R.id.toolbar) Toolbar mToolbar;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mToolbar.setTitle(R.string.app_name);

        mFragmentManager = getSupportFragmentManager();
        if (findViewById(R.id.fragment_container_details)!=null) {
            mTwoPane = true;
        }
        Fragment fragment = mFragmentManager.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = new PopularMovieFragment();
            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, new PopularMovieFragment(), "POPULAR")
                    .commit();
        }

        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_popular:
                        mFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, new PopularMovieFragment(), "POPULAR")
                                .commit();
                        break;
                    case R.id.tab_top_rated:
                        mFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, new TopRatedMovieFragment(), "TOP_RATED")
                                .commit();
                        break;
                    case R.id.tab_favorites:
                        mFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, new FavoriteMovieFragment(), "FAVORITE")
                                .commit();
                        break;
                    default:

                }
            }
        });

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onAdapterItemClick(int id,Bundle bundle) {

        if (!mTwoPane) {

            Intent intent = new Intent(this, MovieDetailActivity.class);
            intent.putExtra("Movie", id);
            startActivity(intent, bundle);

        }else {
            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_details,MovieDetailFragment.newInstance(id))
                    .commit();

        }

    }
}
